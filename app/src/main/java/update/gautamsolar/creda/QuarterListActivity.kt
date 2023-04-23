package update.gautamsolar.creda

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import dagger.hilt.android.AndroidEntryPoint
import update.gautamsolar.creda.models.Data
import update.gautamsolar.creda.retrofit.ui.MainViewModel
import update.gautamsolar.creda.retrofit.util.ApiState
import update.gautamsolar.creda.ui.theme.CredaAppTheme

@AndroidEntryPoint
class QuarterListActivity  : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    var mutableState by mutableStateOf(listOf<Data>())
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CredaAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                    mainViewModel.getOrderList(sharedPreferences.getString("eng_id", "")!!)
                       Column( modifier = Modifier.background(colorResource(id = R.color.dim_white)).fillMaxHeight(1f)) {
                           List()
                       }
                    GETList(mainViewModel)
                }
            }
        }

    }
    @Composable
    fun List(){
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        SearchView(textState)
        val orders = mutableState
        var filteredList: List<Data>
        var resultList: ArrayList<Data> = ArrayList<Data>()

        LazyColumn(modifier = Modifier.fillMaxSize(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {

            val searchedText = textState.value.text
            if (orders != null) {
                if (searchedText.isEmpty()) {
                    filteredList = orders!!
                } else {
                    resultList.clear()

                    for (items in orders!!) {
                        if (items.fname.toString().lowercase().contains(searchedText.lowercase())||
                            items.reg_no.toString().lowercase().contains(searchedText.lowercase())||
                            items.saral_no.toString().lowercase().contains(searchedText.lowercase())
                        ) {
                            resultList.add(items)
                        }
                    }
                    filteredList = resultList
                }

                items(
                    items = filteredList,
                    itemContent = {
                        OrdersListItem(orders = it)
                    })
            }

        }
    }

    @Composable
    fun OrdersListItem(orders: Data) {
        Card(
            modifier = Modifier
                .padding(horizontal = 1.dp, vertical = 8.dp)
                .clickable(onClick = {
                    startActivity(
                        Intent(this@QuarterListActivity, QuarterlyDetailUpdate::class.java)
                            .putExtra("name", orders.fname)
                            .putExtra("fname", orders.fathername)
                            .putExtra("reg_no", orders.reg_no)
                            .putExtra("block", orders.block)
                            .putExtra("dist", orders.district)
                            .putExtra("village", orders.village)
                            .putExtra("phase", orders.phase)
                            .putExtra("pump_capacity", orders.pump_capacity)
                            .putExtra("contact", orders.contact_no)
                            .putExtra("saral_no",orders.saral_no)
                            .putExtra("controller_sr_no",orders.controller_srno)
                            .putExtra("rms_id",orders.controler_rms_id)
                            .putExtra("motor_sr_no",orders.motor_srno)
                            .putExtra("img3",orders.controller_image_1)
                            .putExtra("img1",orders.quaterly_reportimage_1)
                            .putExtra("img2",orders.panel_image_1)
                            .putExtra("pumpHead",orders.pump_head)
                            .putExtra("gender",orders.gender)
                            .putExtra("category",orders.category)
                            .putExtra("system_available_status",orders.system_available_status)
                            .putExtra("remarks",orders.remarks)
                    )
                    Log.d("clicked", "itemClick")
                })
                .fillMaxWidth(),
            elevation = 0.5.dp,
            backgroundColor = colorResource(id = R.color.white),
            shape = RoundedCornerShape(corner = CornerSize(5.dp))

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
                                text = "नाम: "+orders.fname.toString(),
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
                                text = orders.reg_no.toString(),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Text(
                        text = "पिता का नाम: "+orders.fathername.toString(),
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
                                text = orders.contact_no.toString(),
                                fontSize = 15.sp,
                                modifier = Modifier.absolutePadding(5.dp, 0.dp, 5.dp, 2.dp),
                                color = colorResource(id = R.color.white)
                            )
                        }
                        Text(
                            text ="पंप: "+ orders.pump_capacity.toString(),
                            fontSize = 15.sp,
                            modifier = Modifier.absolutePadding(5.dp, 0.dp, 5.dp, 0.dp),
                            color = colorResource(id = R.color.black),
                        )
                    }
                    Row(){
                        Text(
                            text = "जिला: "+orders.district.toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "ब्लॉक: "+orders.block.toString(),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row() {
                        Text(
                            text = "गांव: " + orders.village.toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "फेज: " + orders.phase.toString(),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "सरल नंबर: " + orders.saral_no.toString(),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(0.dp,5.dp,0.dp,0.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider(color = colorResource(id = R.color.black), thickness = 0.5.dp)

            }
        }
    }

    @Composable
    fun SearchView(state: MutableState<TextFieldValue>) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {


            TextField(
                value = state.value,
                onValueChange = { value ->
                    state.value = value
                },
                modifier = Modifier
                    .fillMaxWidth(1F)
                    .padding(15.dp, 15.dp, 10.dp, 15.dp)
                    .height(50.dp)
                    .border(1.dp, colorResource(id = R.color.black), RoundedCornerShape(10.dp)),

                placeholder = {
                    Text(
                        text = "Search here..",
                        color = colorResource(id = R.color.black),
                        fontSize = 15.sp
                    )
                },

                textStyle = TextStyle(
                    color = colorResource(id = R.color.black),
                    fontSize = 15.sp
                ),

                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp)
                    )
                },
                trailingIcon = {
                    if (state.value != TextFieldValue("")) {
                        IconButton(
                            onClick = {
                                state.value =
                                    TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(
                    corner = androidx.compose.foundation.shape.CornerSize(
                        15.dp
                    )
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.black),
                    cursorColor = colorResource(id = R.color.black),
                    leadingIconColor = colorResource(id = R.color.black),
                    trailingIconColor = colorResource(id = R.color.black),
                    backgroundColor = colorResource(id = R.color.white),
                    focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                )
            )

        }

    }


    @Composable
    fun GETList(mainViewModel: MainViewModel) {
        when (val result = mainViewModel.response.value) {
            is ApiState.QuarterlyList -> {
                if (result.data.status.equals("1")) {
                    mutableState = result.data.data!!
                } else {
                    mutableState = emptyList()
                    Toast.makeText(
                        this,
                        result.data.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                mainViewModel.response.value = ApiState.Empty

            }
            is ApiState.Failure -> {
                mainViewModel.response.value = ApiState.Empty
                Log.d("LoginServerResponse", result.msg.toString())
            }
            ApiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            ApiState.Empty -> {

            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        mainViewModel.getOrderList(sharedPreferences.getString("eng_id", "")!!)
    }
}
