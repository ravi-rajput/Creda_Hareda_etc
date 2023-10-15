package update.gautamsolar.creda

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import update.gautamsolar.creda.retrofit.ui.MainViewModel
import update.gautamsolar.creda.retrofit.util.ApiState
import update.gautamsolar.creda.ui.theme.CredaAppTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.timerTask


@AndroidEntryPoint
class QuarterlyDetailUpdate : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    var controllerNo by mutableStateOf("")
    var RmsID by mutableStateOf("")
    var remark by mutableStateOf("")
    var motorNo by mutableStateOf("")
    var lattitude by mutableStateOf("")
    var longitude by mutableStateOf("")
    var gender by mutableStateOf("")
    var catagory by mutableStateOf("")
    var systemStatus by mutableStateOf("")
    var pumpHead by mutableStateOf("")
    private val PERMISSIONS_REQUEST_CODE = 10
    private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION)
    val radioOptions = listOf("Male", "Female")
    val radioCatagory = listOf("General", "OBC", "SC", "ST")
    val radioSystemStatus = listOf("YES", "NO")
    val radioPumphead = listOf("30", "50", "70", "100")
    var img1:String="null"
    var img2:String="null"
    var img3:String="null"
    var img4:String="null"
    var img5:String="null"
    var img:String=""
    private var gpsTracker: GpsTracker? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    lateinit var sharedPreferences: SharedPreferences

    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)

    var pumpHeadSelected:Int? = null
    var genderSelected:Int? = null
    var cateSelected:Int? = null
    var SystemStatusSelected:Int? = null
    private  var photoBitmap: Bitmap?=null
    private  var photoBitmap2: Bitmap?=null
    private  var photoBitmap3: Bitmap?=null
    private  var photoBitmap4: Bitmap?=null
    private  var photoBitmap5: Bitmap?=null
    var date:String = ""
    private var shouldButton: MutableState<Boolean> = mutableStateOf(true)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("kilo", "Permission granted")
        } else {
            Log.i("kilo", "Permission denied")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CredaAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    requestCameraPermission()
                    val coroutineScope = rememberCoroutineScope()
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                    controllerNo = intent.getStringExtra("controller_sr_no").toString()
                    RmsID = intent.getStringExtra("rms_id").toString()
                     remark = intent.getStringExtra("remarks").toString()
                    motorNo = intent.getStringExtra("motor_sr_no").toString()
                    date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

                    if(intent.getStringExtra("pumpHead").toString().equals("30")){
                        pumpHeadSelected = 0
                    }else if(intent.getStringExtra("pumpHead").toString().equals("50")){
                        pumpHeadSelected = 1
                    }else if(intent.getStringExtra("pumpHead").toString().equals("70")){
                        pumpHeadSelected = 2
                    }else if(intent.getStringExtra("pumpHead").toString().equals("100")){
                        pumpHeadSelected = 3
                    }

                    if(intent.getStringExtra("gender").toString().equals("Male")){
                        genderSelected = 0
                    }else if(intent.getStringExtra("gender").toString().equals("Female")){
                        genderSelected = 1
                    }
                    if(intent.getStringExtra("system_available_status").toString().equals("YES")){
                        SystemStatusSelected = 0
                    }else if(intent.getStringExtra("system_available_status").toString().equals("NO")){
                        SystemStatusSelected = 1
                    }
                    if(intent.getStringExtra("category").toString().equals("General")){
                        cateSelected = 0
                    }else if(intent.getStringExtra("category").toString().equals("OBC")){
                        cateSelected = 1
                    }else if(intent.getStringExtra("category").toString().equals("SC")){
                        cateSelected = 2
                    }else if(intent.getStringExtra("category").toString().equals("ST")){
                        cateSelected = 3
                    }
                        val scroll = rememberScrollState(0)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .background(colorResource(id = R.color.dim_white))
                                    .fillMaxHeight(1f)
                                    .verticalScroll(scroll)
                                    .padding(0.dp, 0.dp, 0.dp, 70.dp)
                            ) {
                                showUI()
                                updateData()
                            }
                            MyButton()

                    UpdateData(mainViewModel = mainViewModel)

                outputDirectory = getOutputDirectory()
                cameraExecutor = Executors.newSingleThreadExecutor()
                    BackHandler() {
                        coroutineScope.launch {
                            if (shouldShowCamera.value) {
                                shouldShowCamera.value = false
                            } else {
                                finish()
                            }
                        }
                    }
                    if (shouldShowCamera.value) {
                            CameraView(
                                outputDirectory = outputDirectory,
                                executor = cameraExecutor,
                                onImageCaptured = ::handleImageCapture
                            ) { Log.e("kilo", "View error:", it) }
                    }
                }
            }
        }
    }

    @Composable
    fun showUI() {
        Card(
            modifier = Modifier
                .padding(horizontal = 1.dp, vertical = 8.dp)
                .fillMaxWidth()
                .padding(5.dp),
            elevation = 0.5.dp,
            backgroundColor = colorResource(id = R.color.iron),
            shape = RoundedCornerShape(corner = CornerSize(5.dp)),

            ) {
            Column {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Row {
                        Column() {
                            Text(
                                text = "नाम: " + intent.getStringExtra("name"),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = intent.getStringExtra("reg_no").toString(),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Text(
                        text = "पिता का नाम: " + intent.getStringExtra("fname"),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            elevation = 0.5.dp,
                            backgroundColor = colorResource(id = R.color.black),
                            shape = RoundedCornerShape(
                                corner = CornerSize(
                                    5.dp
                                )
                            )

                        ) {
                            Text(
                                text = intent.getStringExtra("contact").toString(),
                                fontSize = 15.sp,
                                modifier = Modifier.absolutePadding(5.dp, 0.dp, 5.dp, 2.dp),
                                color = colorResource(id = R.color.white)
                            )
                        }
                        Text(
                            text = "पंप: " + intent.getStringExtra("pump_capacity").toString(),
                            fontSize = 15.sp,
                            modifier = Modifier.absolutePadding(5.dp, 0.dp, 5.dp, 0.dp),
                            color = colorResource(id = R.color.black),
                        )
                    }
                    Row() {
                        Text(
                            text = "जिला: " + intent.getStringExtra("dist").toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "ब्लॉक: " + intent.getStringExtra("block"),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row() {
                        Text(
                            text = "गांव: " + intent.getStringExtra("village").toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "फेज: " + intent.getStringExtra("phase").toString(),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "सरल नंबर: " + intent.getStringExtra("saral_no").toString(),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider(color = colorResource(id = R.color.black), thickness = 0.5.dp)

            }

        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun updateData() {
        getLocation()
        val (selectedOption, onOptionSelected) = remember {
                mutableStateOf(
                    if(genderSelected!=null) {
                        radioOptions[genderSelected!!]
                    }else{
                        null
                    }
                )
        }
        val (selectedCate, onCateSelected) = remember { mutableStateOf( if(cateSelected!=null) {radioCatagory[cateSelected!!]}else{null}) }
        val (selectedProductStatus, onSystemSelected) = remember { mutableStateOf( if(SystemStatusSelected!=null) { radioSystemStatus[SystemStatusSelected!!]}else{
            null
        }) }
        val (selectedPump, onPumpSelected) = remember { mutableStateOf(
            if(pumpHeadSelected!=null) { radioPumphead[pumpHeadSelected!!]}else{
                null
            }
        ) }

        Row(horizontalArrangement = Arrangement.Center) {
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(5.dp)
                    .clickable {
                        img = "1"
                        shouldShowCamera.value = true
                    },
                backgroundColor = colorResource(id = R.color.white),
                elevation = 0.5.dp,
                shape = RoundedCornerShape(corner = CornerSize(5.dp)),
            ) {

                    Image(
                        painter = rememberImagePainter(if(photoBitmap!=null) photoBitmap else intent.getStringExtra("img1").toString()),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                    )

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom) {
                    Text(text = "क्वार्टरली रिपोर्ट", fontSize = 13.sp,
                        modifier = Modifier.padding(5.dp,10.dp,5.dp,0.dp),
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                }
              }
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(5.dp)
                    .clickable {
                        img = "2"
                        shouldShowCamera.value = true
                    },
                backgroundColor = colorResource(id = R.color.white),
                elevation = 0.5.dp,
                shape = RoundedCornerShape(corner = CornerSize(5.dp))
            ) {


                    Image(
                        painter = rememberImagePainter(if(photoBitmap2!=null) photoBitmap2 else intent.getStringExtra("img2").toString()),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom) {
                    Text(text = "पैनल इमेज", fontSize = 13.sp, modifier = Modifier.padding(5.dp,10.dp,5.dp,0.dp),
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold)

                }
            }
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(5.dp)
                    .clickable {
                        img = "3"
                        shouldShowCamera.value = true
                    },
                backgroundColor = colorResource(id = R.color.white),
                elevation = 0.5.dp,
                shape = RoundedCornerShape(corner = CornerSize(5.dp))
            ) {

                    Image(
                        painter = rememberImagePainter(if(photoBitmap3!=null) photoBitmap3 else intent.getStringExtra("img3").toString()),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom) {
                    Text(text = "कंट्रोलर इमेज", fontSize = 13.sp, modifier = Modifier.padding(5.dp,10.dp,5.dp,0.dp),
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold)

                }
            }
        }


        Row(horizontalArrangement = Arrangement.Center) {
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(5.dp)
                    .clickable {
                        img = "4"
                        shouldShowCamera.value = true
                    },
                backgroundColor = colorResource(id = R.color.white),
                elevation = 0.5.dp,
                shape = RoundedCornerShape(corner = CornerSize(5.dp)),
            ) {

                Image(
                    painter = rememberImagePainter(if(photoBitmap4!=null) photoBitmap4 else intent.getStringExtra("img4").toString()),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom) {
                    Text(text = "फाइनल इमेज फाउंडेशन", fontSize = 13.sp,
                        modifier = Modifier.padding(5.dp,10.dp,5.dp,0.dp),
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(5.dp)
                    .clickable {
                        img = "5"
                        shouldShowCamera.value = true
                    },
                backgroundColor = colorResource(id = R.color.white),
                elevation = 0.5.dp,
                shape = RoundedCornerShape(corner = CornerSize(5.dp))
            ) {


                Image(
                    painter = rememberImagePainter(if(photoBitmap5!=null) photoBitmap5 else intent.getStringExtra("img5").toString()),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom) {
                    Text(text = "water discharge", fontSize = 13.sp, modifier = Modifier.padding(5.dp,10.dp,5.dp,0.dp),
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold)

                }
            }
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth(1F)
                .padding(15.dp, 10.dp, 15.dp, 0.dp)
                .border(1.dp, colorResource(id = R.color.black), RoundedCornerShape(6.dp)),
            value = controllerNo,
            onValueChange = {
                controllerNo = it
            },
            label = { Text(text = "कंट्रोलर सीरियल नंबर") },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),

            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.white),
                cursorColor = Color.Black.copy(alpha = ContentAlpha.high)
            )
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth(1F)
                .padding(15.dp, 10.dp, 15.dp, 0.dp)
                .border(1.dp, colorResource(id = R.color.black), RoundedCornerShape(6.dp)),
            value = RmsID,
            onValueChange = {
                RmsID = it
            },
            label = { Text(text = "आरएमएस Id") },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),

            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.white),
                cursorColor = Color.Black.copy(alpha = ContentAlpha.high)
            )
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth(1F)
                .padding(15.dp, 10.dp, 15.dp, 0.dp)
                .border(1.dp, colorResource(id = R.color.black), RoundedCornerShape(6.dp)),
            value = motorNo,
            onValueChange = {
                motorNo = it
            },
            label = { Text(text = "मोटर सीरियल नंबर") },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),

            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.white),
                cursorColor = Color.Black.copy(alpha = ContentAlpha.high)
            )
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Select Pump Head", modifier = Modifier.padding(10.dp))
            Divider(
                color = colorResource(id = R.color.black),
                thickness = 0.5.dp,
                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)
            )
            radioPumphead.forEach { text ->
                Row(
                    Modifier
                        // using modifier to add max
                        // width to our radio button.
                        .fillMaxWidth()
                        // below method is use to add
                        // selectable to our radio button.
                        .selectable(
                            // this method is called when
                            // radio button is selected.
                            selected = (text == selectedPump),
                            // below method is called on
                            // clicking of radio button.
                            onClick = {
                                onPumpSelected(text)
                                pumpHead = text
                            }
                        )
                        // below line is use to add
                        // padding to radio button.
                        .padding(horizontal = 16.dp)
                ) {
                    // below line is use to get context.
                    val context = LocalContext.current

                    // below line is use to
                    // generate radio button
                    RadioButton(
                        // inside this method we are
                        // adding selected with a option.
                        selected = (text == selectedPump),
                        modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {
                            // inide on click method we are setting a
                            // selected option of our radio buttons.
                            onPumpSelected(text)
                            pumpHead = text
                            // after clicking a radio button
                            // we are displaying a toast message.
//                            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                        }
                    )
                    // below line is use to add
                    // text to our radio buttons.
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Select Gender", modifier = Modifier.padding(10.dp))
            Divider(
                color = colorResource(id = R.color.black),
                thickness = 0.5.dp,
                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)
            )
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        // using modifier to add max
                        // width to our radio button.
                        .fillMaxWidth()
                        // below method is use to add
                        // selectable to our radio button.
                        .selectable(
                            // this method is called when
                            // radio button is selected.
                            selected = (text == selectedOption),
                            // below method is called on
                            // clicking of radio button.
                            onClick = {
                                onOptionSelected(text)
                                gender = text
                            }
                        )
                        // below line is use to add
                        // padding to radio button.
                        .padding(horizontal = 16.dp)
                ) {
                    // below line is use to get context.
                    val context = LocalContext.current

                    // below line is use to
                    // generate radio button
                    RadioButton(
                        // inside this method we are
                        // adding selected with a option.
                        selected = (text == selectedOption),
                        modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {
                            // inide on click method we are setting a
                            // selected option of our radio buttons.
                            onOptionSelected(text)
                             gender = text
                            // after clicking a radio button
                            // we are displaying a toast message.
//                            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                        }
                    )
                    // below line is use to add
                    // text to our radio buttons.
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Select categeoty", modifier = Modifier.padding(10.dp))
            Divider(
                color = colorResource(id = R.color.black),
                thickness = 0.5.dp,
                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)
            )
            radioCatagory.forEach { text ->
                Row(
                    Modifier
                        // using modifier to add max
                        // width to our radio button.
                        .fillMaxWidth()
                        // below method is use to add
                        // selectable to our radio button.
                        .selectable(
                            // this method is called when
                            // radio button is selected.
                            selected = (text == selectedCate),
                            // below method is called on
                            // clicking of radio button.
                            onClick = {
                                onCateSelected(text)
                                catagory = text
                            }
                        )
                        // below line is use to add
                        // padding to radio button.
                        .padding(horizontal = 16.dp)
                ) {
                    // below line is use to get context.
                    val context = LocalContext.current

                    // below line is use to
                    // generate radio button
                    RadioButton(
                        // inside this method we are
                        // adding selected with a option.
                        selected = (text == selectedCate),
                        modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {
                            // inide on click method we are setting a
                            // selected option of our radio buttons.
                            onCateSelected(text)
                            catagory = text
                            // after clicking a radio button
                            // we are displaying a toast message.
//                            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                        }
                    )
                    // below line is use to add
                    // text to our radio buttons.
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "सिस्टम पाया गया या नही", modifier = Modifier.padding(10.dp))
            Divider(
                color = colorResource(id = R.color.black),
                thickness = 0.5.dp,
                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)
            )
            radioSystemStatus.forEach { text ->
                Row(
                    Modifier
                        // using modifier to add max
                        // width to our radio button.
                        .fillMaxWidth()
                        // below method is use to add
                        // selectable to our radio button.
                        .selectable(
                            // this method is called when
                            // radio button is selected.
                            selected = (text == selectedProductStatus),
                            // below method is called on
                            // clicking of radio button.
                            onClick = {
                                onCateSelected(text)
                                systemStatus = text
                            }
                        )
                        // below line is use to add
                        // padding to radio button.
                        .padding(horizontal = 16.dp)
                ) {
                    // below line is use to get context.
                    val context = LocalContext.current

                    // below line is use to
                    // generate radio button
                    RadioButton(
                        // inside this method we are
                        // adding selected with a option.
                        selected = (text == selectedProductStatus),
                        modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {
                            // inide on click method we are setting a
                            // selected option of our radio buttons.
                            onSystemSelected(text)
                            systemStatus = text
                            // after clicking a radio button
                            // we are displaying a toast message.
//                            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                        }
                    )
                    // below line is use to add
                    // text to our radio buttons.
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth(1F)
                .padding(15.dp, 10.dp, 15.dp, 0.dp)
                .border(1.dp, colorResource(id = R.color.black), RoundedCornerShape(6.dp)),
            value = remark,
            onValueChange = {
                remark = it
            },
            label = { Text(text = "कोई रिमार्क्स") },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),

            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.white),
                cursorColor = Color.Black.copy(alpha = ContentAlpha.high)
            )
        )


    }

    @Composable
    fun MyButton() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if(shouldButton.value) {
                Button(
                    onClick = {
                        shouldButton.value = false
                        if (!hasPermissions(this@QuarterlyDetailUpdate)) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(
                                    PERMISSIONS_REQUIRED,
                                    PERMISSIONS_REQUEST_CODE
                                )
                            }
                        } else {
                            Timer().schedule(timerTask {
                                val params = HashMap<String, String>()
                                params.put("eng_id", sharedPreferences.getString("eng_id", "")!!)
                                params.put("lat", lattitude)
                                params.put("lon", longitude)
                                params.put("quaterly_reportimage_1", img1)
                                params.put("panel_image_1", img2)
                                params.put("Controller_image_1", img3)
                                params.put("Final_image_Foundation", img4)
                                params.put("water_discharge", img5)
                                params.put("controller_srno", controllerNo)
                                params.put("controler_rms_id", RmsID)
                                params.put("remarks", remark)
                                params.put("motor_srno", motorNo)
                                params.put("datetime", date)
                                if (gender.equals("")) {
                                    params.put("gender", intent.getStringExtra("gender").toString())
                                } else {
                                    params.put("gender", gender)
                                }
                                if (pumpHead.equals("")) {
                                    params.put(
                                        "pump_head",
                                        intent.getStringExtra("pumpHead").toString()
                                    )
                                } else {
                                    params.put("pump_head", pumpHead)
                                }
                                if (catagory.equals("")) {
                                    params.put(
                                        "category",
                                        intent.getStringExtra("category").toString()
                                    )
                                } else {
                                    params.put("category", catagory)
                                }
                                if (systemStatus.equals("")) {
                                    params.put(
                                        "system_available_status",
                                        intent.getStringExtra("system_available_status").toString()
                                    )
                                } else {
                                    params.put("system_available_status", systemStatus)
                                }
                                params.put("reg_no", intent.getStringExtra("reg_no").toString())
//Log.d("RequestParams",img1+img2+"....."+pumpHead+"......"+
//        controllerNo+"......"+"...."+RmsID+"...."+motorNo+"....."+gender+"....."+catagory+"...."+intent.getStringExtra("reg_no").toString())
                                mainViewModel.postDetails(params)
                            }, 500)

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(42.dp),
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.black)),
                )
                {
                    Text(
                        text = "Update",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 12.sp,
                        )
                    )
                }
            }
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("kilo", "Permission previously granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i("kilo", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun handleImageCapture(bitmap: Bitmap,file: File) {
        shouldShowCamera.value = false

        if(img.equals("1")){
            photoBitmap = bitmap
            img1 = mainViewModel.convertImageFileToBase64(printOnImage(bitmap))
         }else if(img.equals("2")){
            photoBitmap2 = bitmap
            img2 = mainViewModel.convertImageFileToBase64(printOnImage(bitmap))
        }else if(img.equals("3")){
            photoBitmap3 = bitmap
             img3 = mainViewModel.convertImageFileToBase64(printOnImage(bitmap))
            Log.d("bitmapcallinh","callingbitmal")
        }else if(img.equals("4")){
            photoBitmap4 = bitmap
             img4 = mainViewModel.convertImageFileToBase64(printOnImage(bitmap))
            Log.d("bitmapcallinh","callingbitmal")
        }else if(img.equals("5")){
            photoBitmap5 = bitmap
             img5 = mainViewModel.convertImageFileToBase64(printOnImage(bitmap))
            Log.d("bitmapcallinh","callingbitmal")
        }


//        shouldShowPhoto.value = true
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }


    fun getLocation() {
        gpsTracker = GpsTracker(this)
        if (gpsTracker!!.canGetLocation()) {
            val lati: Double = gpsTracker!!.getLatitude()
            val longi: Double = gpsTracker!!.getLongitude()
            lattitude = lati.toString()
            longitude = longi.toString()
        } else {
            gpsTracker!!.showSettingsAlert()
        }
    }

    fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    @Composable
    fun UpdateData(mainViewModel: MainViewModel) {
        when (val result = mainViewModel.response.value) {
            is ApiState.QuarterlyList -> {
                if (result.data.error==false) {
                    finish()
                }else{
                    shouldButton.value = true
                }
                Toast.makeText(
                    this,
                    result.data.message,
                    Toast.LENGTH_SHORT
                ).show()
                mainViewModel.response.value = ApiState.Empty

            }
            is ApiState.Failure -> {
                shouldButton.value = true
                mainViewModel.response.value = ApiState.Empty
                Log.d("QuarterlyUpdate", result.msg.toString())
            }
            ApiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
                Log.d("QuarterlyUpdate", "Loading")
            }
            ApiState.Empty -> {
                Log.d("QuarterlyUpdate", "Empty")
            }
        }
    }

    fun printOnImage(bitmap: Bitmap) :Bitmap{
        val result = bitmap.rotate(90f)
        val canvas = android.graphics.Canvas(result)
        val paint = Paint()
        paint.color = android.graphics.Color.BLACK
        paint.textSize = 100f
        paint.isAntiAlias = false
        val innerPaint = Paint()
        innerPaint.color = android.graphics.Color.parseColor("#61ECECEC")
        innerPaint.isAntiAlias = false
        canvas.drawRect(
            1500f,
            750f,
            0f,
           0f,
            innerPaint
        )
        canvas.drawText("Saral Id - ${intent.getStringExtra("saral_no").toString()}", 5f, 75f, paint)
        canvas.drawText("Farmer Name - ${intent.getStringExtra("name")}", 5f, 175f, paint)
        canvas.drawText("District - ${intent.getStringExtra("dist").toString()}", 5f, 275f, paint)
        canvas.drawText("Pump Capacity - ${intent.getStringExtra("pump_capacity").toString()}", 5f, 375f, paint)
       if(intent.getStringExtra("phase").equals("HAREDA_PHASE1")){
           canvas.drawText("Lat - ${intent.getStringExtra("lat_hareda")}", 5f, 475f, paint)
           canvas.drawText("Long - ${intent.getStringExtra("long_hareda")}", 5f, 575f, paint)
       }else {
           canvas.drawText("Lat - $lattitude", 5f, 475f, paint)
           canvas.drawText("Long - $longitude", 5f, 575f, paint)
       }
        canvas.drawText("Date - $date", 5f, 675f, paint)
        return result

    }

    fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }
}
