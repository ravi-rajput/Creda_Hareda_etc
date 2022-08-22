package update.gautamsolar.creda

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
class NewInstalationActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val PERMISSIONS_REQUEST_CODE = 10
    private val PERMISSIONS_REQUIRED =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)

    var img1: String = "null"
    var img2: String = "null"
    var img3: String = "null"
    var img4: String = "null"
    var img5: String = "null"
    var img: String = ""
    var lattitude by mutableStateOf("")
    var longitude by mutableStateOf("")
    private var gpsTracker: GpsTracker? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    lateinit var sharedPreferences: SharedPreferences

    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)
    private var shouldShowGalary: MutableState<Boolean> = mutableStateOf(false)
    private var photoBitmap: Bitmap? = null
    private var photoBitmap2: Bitmap? = null
    private var photoBitmap3: Bitmap? = null
    private var photoBitmap4: Bitmap? = null
    private var photoBitmap5: Bitmap? = null
    var site_lat_new:String ?= null
    var site_long_new:String ?= null
   var savedImage1:String ?= null
   var savedImage2:String ?= null
   var savedImage3:String ?= null
   var savedImage4:String ?= null
   var savedImage5:String ?= null
    var date: String = ""
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
                    val openDialog = remember { mutableStateOf(false)  }
                    val coroutineScope = rememberCoroutineScope()
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                    site_lat_new = sharedPreferences.getString("site_lat_new", "")
                    site_long_new = sharedPreferences.getString("site_long_new", "")
                    savedImage1 = sharedPreferences.getString("peda_image1", "")
                    savedImage2 = sharedPreferences.getString("peda_image2", "")
                    savedImage3 = sharedPreferences.getString("peda_image3", "")
                    savedImage4 = sharedPreferences.getString("peda_image4", "")
                    savedImage5 = sharedPreferences.getString("peda_image5", "")
                    date =
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

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
                        updateData(openDialog)
                    }
                    MyButton()

                    UpdateData(mainViewModel = mainViewModel)

                    outputDirectory = getOutputDirectory()
                    cameraExecutor = Executors.newSingleThreadExecutor()
                    BackHandler() {
                        coroutineScope.launch {
                            if (shouldShowCamera.value) {
                                shouldShowCamera.value = false
                            }else if(shouldShowGalary.value) {
                                shouldShowGalary.value = false
                            }
                            else {
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
                    var imageUri by remember {
                        mutableStateOf<Uri?>(null)
                    }
                    val context = LocalContext.current

                    val launcher = rememberLauncherForActivityResult(
                        contract =
                        ActivityResultContracts.GetContent()
                    ) { uri: Uri? ->
                        imageUri = uri
                        shouldShowGalary.value = true
                    }
                    var bitmap = remember {
                        mutableStateOf<Bitmap?>(null)
                    }
                    AlertDialogSample(launcher, openDialog,bitmap)
                if(shouldShowGalary.value) {

                    imageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            bitmap.value = MediaStore.Images
                                .Media.getBitmap(context.contentResolver, it)

                        } else {
                            var source = ImageDecoder
                                .createSource(context.contentResolver, it)
                            bitmap.value = ImageDecoder.decodeBitmap(source)
                        }

                        bitmap.value?.let { btm ->
                            shouldShowGalary.value=false
                            val bmp_Copy: Bitmap = btm.copy(Bitmap.Config.ARGB_8888, true)
                            setImageCaptureData(bmp_Copy)
                        }
                    }
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
                                text = "Reg No - " + intent.getStringExtra("reg_no").toString(),
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
                    }
                    Row() {

                        Text(
                            text = "ब्लॉक: " + intent.getStringExtra("block"),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = " || गांव: " + intent.getStringExtra("village").toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Divider(color = colorResource(id = R.color.black), thickness = 0.5.dp)

            }

        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun updateData(openDialog: MutableState<Boolean>) {


        getLocation()
        Column() {
            Row(horizontalArrangement = Arrangement.Center) {
                Card(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .padding(5.dp)
                        .clickable {
                            img = "1"
                            openDialog.value = true
                        },
                    backgroundColor = colorResource(id = R.color.white),
                    elevation = 0.5.dp,
                    shape = RoundedCornerShape(corner = CornerSize(5.dp)),
                ) {

                    Image(
                        painter = rememberImagePainter(
                            if (photoBitmap != null) photoBitmap else savedImage1
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "Motor foundation pic", fontSize = 13.sp,
                            modifier = Modifier.padding(5.dp, 10.dp, 5.dp, 0.dp),
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
                            openDialog.value = true
                        },
                    backgroundColor = colorResource(id = R.color.white),
                    elevation = 0.5.dp,
                    shape = RoundedCornerShape(corner = CornerSize(5.dp))
                ) {


                    Image(
                        painter = rememberImagePainter(
                            if (photoBitmap2 != null) photoBitmap2 else savedImage2
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "motor foundation pic with farmer",
                            fontSize = 13.sp,
                            modifier = Modifier.padding(5.dp, 10.dp, 5.dp, 0.dp),
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
                            img = "3"
                            openDialog.value = true
                        },
                    backgroundColor = colorResource(id = R.color.white),
                    elevation = 0.5.dp,
                    shape = RoundedCornerShape(corner = CornerSize(5.dp))
                ) {

                    Image(
                        painter = rememberImagePainter(
                            if (photoBitmap3 != null) photoBitmap3 else savedImage3
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "motor earthing pic",
                            fontSize = 13.sp,
                            modifier = Modifier.padding(5.dp, 10.dp, 5.dp, 0.dp),
                            color = colorResource(id = R.color.black),
                            fontWeight = FontWeight.Bold
                        )

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
                            openDialog.value = true
                        },
                    backgroundColor = colorResource(id = R.color.white),
                    elevation = 0.5.dp,
                    shape = RoundedCornerShape(corner = CornerSize(5.dp))
                ) {

                    Image(
                        painter = rememberImagePainter(
                            if (photoBitmap4 != null) photoBitmap4 else savedImage4
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "structure earthing pic",
                            fontSize = 13.sp,
                            modifier = Modifier.padding(5.dp, 10.dp, 5.dp, 0.dp),
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
                            openDialog.value = true
                        },
                    backgroundColor = colorResource(id = R.color.white),
                    elevation = 0.5.dp,
                    shape = RoundedCornerShape(corner = CornerSize(5.dp))
                ) {

                    Image(
                        painter = rememberImagePainter(
                            if (photoBitmap5 != null) photoBitmap5 else savedImage5
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "Transaction details of farmer",
                            fontSize = 13.sp,
                            modifier = Modifier.padding(5.dp, 10.dp, 5.dp, 0.dp),
                            color = colorResource(id = R.color.black),
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            }
//            Row(horizontalArrangement = Arrangement.Center) {
//                Card(
//                    modifier = Modifier
//                        .width(300.dp)
//                        .height(120.dp)
//                        .padding(5.dp)
//                        .clickable {
//                            img = "5"
//                            shouldShowCamera.value = true
//                        },
//                    backgroundColor = colorResource(id = R.color.white),
//                    elevation = 0.5.dp,
//                    shape = RoundedCornerShape(corner = CornerSize(5.dp))
//                ) {
//
//                    Image(
//                        painter = rememberImagePainter(
//                            if (photoBitmap5 != null) photoBitmap5 else intent.getStringExtra(
//                                "img5"
//                            ).toString()
//                        ),
//                        contentDescription = null,
//                        modifier = Modifier.fillMaxSize()
//                    )
//
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Bottom
//                    ) {
//                        Text(
//                            text = "Record Video",
//                            fontSize = 13.sp,
//                            modifier = Modifier.padding(5.dp, 10.dp, 5.dp, 0.dp),
//                            color = colorResource(id = R.color.black),
//                            fontWeight = FontWeight.Bold
//                        )
//
//                    }
//                }
//            }
        }
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
            if (shouldButton.value) {
                Button(
                    onClick = {
                        shouldButton.value = false
                        if (!hasPermissions(this@NewInstalationActivity)) {
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
                                params.put("photo1", img1)
                                params.put("photo2", img2)
                                params.put("photo3", img3)
                                params.put("photo4", img4)
                                params.put("photo5", img5)
                                params.put("datetime", date)
                                params.put("reg_no", intent.getStringExtra("reg_no").toString())
//Log.d("RequestParams",img1+img2+"....."+pumpHead+"......"+
//        controllerNo+"......"+"...."+RmsID+"...."+motorNo+"....."+gender+"....."+catagory+"...."+intent.getStringExtra("reg_no").toString())
                                mainViewModel.postNewInstallation(params)
                            }, 2500)

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

    private fun handleImageCapture(bitmap: Bitmap, file: File) {
        shouldShowCamera.value = false
        setImageCaptureData(bitmap)


//        shouldShowPhoto.value = true
    }
    private fun setImageCaptureData(bitmap: Bitmap) {
        if (img.equals("1")) {
            photoBitmap = bitmap
            img1 = mainViewModel.convertImageFileToBase64(printOnImage(bitmap))
        } else if (img.equals("2")) {
            photoBitmap2 = bitmap
            img2 = mainViewModel.convertImageFileToBase64(printOnImage(bitmap))
        }else if (img.equals("3")) {
            photoBitmap3 = bitmap
            img3 = mainViewModel.convertImageFileToBase64(printOnImage(bitmap))
            Log.d("bitmapcallinh", "callingbitmal")
        }else if (img.equals("4")) {
            photoBitmap4 = bitmap
            img4 = mainViewModel.convertImageFileToBase64(printOnImage(bitmap))
        } else {
            photoBitmap5 = bitmap
            img5 = mainViewModel.convertImageFileToBase64(printOnImage(bitmap))
            Log.d("bitmapcallinh", "callingbitmal")
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
                if (result.data.error == false) {
                    finish()
                } else {
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

    fun printOnImage(bitmap: Bitmap): Bitmap {
        val result = bitmap.rotate(90f)
        val canvas = android.graphics.Canvas(result)
        val paint = Paint()
        paint.color = android.graphics.Color.BLACK
        paint.textSize = 100f
        paint.isAntiAlias = false
        val innerPaint = Paint()
        innerPaint.color = android.graphics.Color.parseColor("#61ECECEC")
        innerPaint.isAntiAlias = false
        canvas.drawRect(180f, 50f, 0f, 0f, innerPaint)
        if (!TextUtils.isEmpty(site_lat_new)&&(site_lat_new!!.length > 4 && site_long_new!!.length > 4)) {
            canvas.drawRect(880f, 250f, 0f, 0f, innerPaint)
            canvas.drawText("Lat - $site_lat_new", 5f, 75f, paint)
            canvas.drawText("Long - $site_long_new", 5f, 170f, paint)
        }
//        canvas.drawText("Date - $date", 5f, 275f, paint)
        return result

    }

    fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }


    @Composable
    fun AlertDialogSample(
        launcher: ManagedActivityResultLauncher<String, Uri?>,
        openDialog: MutableState<Boolean>,
        bitmap: MutableState<Bitmap?>
    ) {
        MaterialTheme {
            Column {

                if (openDialog.value) {

                    AlertDialog(
                        onDismissRequest = {
                            // Dismiss the dialog when the user clicks outside the dialog or on the back
                            // button. If you want to disable that functionality, simply use an empty
                            // onCloseRequest.
                            openDialog.value = false
                        },
                        title = {
                            Text(text = "Click to get image")
                        },
                        confirmButton = {
                            Button(

                                onClick = {
                                    openDialog.value = false
                                    launcher.launch("image/*")
                                }) {
                                Text("Gallary")
                            }
                        },
                        dismissButton = {
                            Button(

                                onClick = {
                                    openDialog.value = false
                                    shouldShowCamera.value = true
                                }) {
                                Text("Camera")
                            }
                        }
                    )
                }
            }

        }
    }

}

