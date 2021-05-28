package com.neuro.neuroharmony.ui.login

import android.widget.AdapterView


class ReligiousInfo : BaseActivity(), AdapterView.OnItemSelectedListener{
//
//    lateinit var viewModel: ReligiousInfoViewModel
//    lateinit var viewModel1: ReligiousInfoSubmitViewModel
//    private lateinit var viewModel2: FamilyInfoViewModel
//    val gson = Gson()
//    var jsonArray = ""
//    var body: MultipartBody.Part? = null
//
//    val STORAGE_REQUEST_CODE = 121
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    @RequiresApi(Build.VERSION_CODES.KITKAT)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_religious_info)
//        PrefsHelper().savePref("Account_Status",4)
//        viewModel = ViewModelProviders.of(this)[ReligiousInfoViewModel::class.java]
//        viewModel1 = ViewModelProviders.of(this)[ReligiousInfoSubmitViewModel::class.java]
//        viewModel2 = ViewModelProviders.of(this)[FamilyInfoViewModel::class.java]
//        setupListerners()
//        setupObservers()
//
//
//
//
//
//        setupListernersSubmit()
//        setupObserversSubmit()
//        setupobserversFamilyinfo()
//        UploadHorscopeButton.setOnClickListener {
//            pickFromDevice()
//        }
//
//
//
//
//
//
//
//            religionSpinner.setOnItemSelectedListener(this);
//        casteSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                subcaste_code()
//
//            }
//
//        }
//
//    }
//    private fun setupListerners() {
//
//        viewModel.religiosInfoLiveData()
//    }
//    private fun setupObservers() {
//        viewModel.religiousInfoResponseLiveData.observe(this, Observer {
//            if (it != null) {
//                if (it.message == "Success") {
//                    jsonArray = gson.toJson(it.data)
//                    val object_main1 = JSONObject(jsonArray)
//                    val obj_main = object_main1.getJSONObject("user_choice")
//                    if(obj_main.has("horoscope")) {
//                        val full_path = obj_main["horoscope"].toString()
//                        val pic_name: String? = full_path.substringAfterLast("/")
//                        UploadHoroscopeEditText.text = Editable.Factory.getInstance().newEditable(pic_name)
//                    }
//                    main()
//
//
//                }
//                else{
//                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//        //observe API call status
//        viewModel.religiousInfoApiCallStatus.observe(this, Observer {
//            processStatus(it)
//        })
//
//    }
//
//    private fun main() {
//        if (jsonArray!=""){
//            main_code(jsonArray)
//            deeply_religious()
//            visiting_temple()
//        }
//
//
//    }
//
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    @RequiresApi(Build.VERSION_CODES.KITKAT)
//    private fun setupListernersSubmit() {
//
//        SaveReligiousInfobutton?.setOnClickListener {
//                if (religionSpinner.selectedItem != "Select" ) {
//                    val object_main1 = JSONObject(jsonArray)
//                    val object_main = object_main1.getJSONObject("choice")
//                    val array = object_main.getJSONArray("religions")
//                    val religion_name: MutableList<String> = arrayListOf()
//                    val religion_id: MutableList<Int> = arrayListOf()
//                    for (i in 0 until array.length()) {
//                        val temp = array.getJSONObject(i)
//                        religion_name.add(temp["religion"].toString())
//                        religion_id.add(temp["id"].toString().toInt())
//                    }
//                    val selectedreligion = religionSpinner.selectedItem.toString()
//                    val pos1 = religion_name.indexOf(selectedreligion)
//                    val religion_id_server = religion_id.get(pos1)
////above is to get religion details selected
//
//                    val deep_array = object_main.getJSONArray("deeply_religious")
//                    val deepreligion_name: MutableList<String> = arrayListOf()
//                    val deepreligion_id: MutableList<Int> = arrayListOf()
//                    for (i in 0 until deep_array.length()) {
//                        val deeptemp = deep_array.getJSONObject(i)
//                        deepreligion_name.add(deeptemp["deeply_religious"].toString())
//                        deepreligion_id.add(deeptemp["id"].toString().toInt())
//                    }
//
//        //above is to get the deep religion
//                    val worship_array = object_main.getJSONArray("worship_place_visit")
//                    val worship_name: MutableList<String> = arrayListOf()
//                    val worship_id: MutableList<Int> = arrayListOf()
//                    for (i in 0 until worship_array.length()) {
//                        val worshiptemp = worship_array.getJSONObject(i)
//                        worship_name.add(worshiptemp["worship_place_visit"].toString())
//                        worship_id.add(worshiptemp["id"].toString().toInt())
//                    }
//
//
//                    for (i in 0 until array.length()) {
//                        val temp = array.getJSONObject(i)
//                        if (temp["religion"] == religionSpinner.selectedItem) {
//                            val caste_array = temp.getJSONArray("caste")
//                            if (caste_array.length() > 0) {
//                                if (casteSpinner.selectedItem != "Select") {//in case of caste exsisting
//
//                                    val caste_name: MutableList<String> = arrayListOf()
//                                    val caste_id: MutableList<Int> = arrayListOf()
//                                    for (caste in 0 until caste_array.length()) {
//                                        val caste_data = caste_array.getJSONObject(caste)
//                                        caste_name.add(caste_data["caste"].toString())
//
//                                        caste_id.add(caste_data["id"].toString().replace(".0","").toInt())
//                                    }
//                                    val selectedcaste = casteSpinner.selectedItem.toString()
//                                    val pos = caste_name.indexOf(selectedcaste)
//                                    val caste_id_server = caste_id.get(pos)
//                                    for (j in 0 until caste_array.length()) {
//                                        val temp1 = caste_array.getJSONObject(j)
//                                        if (temp1["caste"] == casteSpinner.selectedItem) {
//                                            val sub_caste_full = temp1.getJSONArray("sub_caste")
//                                            if (sub_caste_full.length() > 0) {
//                                                if (subCasteSpinner1.selectedItem != "Select") {
//                                                    val sub_caste_name: MutableList<String> =
//                                                        arrayListOf()
//                                                    val sub_caste_id: MutableList<Int> =
//                                                        arrayListOf()
//                                                    for (sub_caste in 0 until sub_caste_full.length()) {
//                                                        val sub_caste_data =
//                                                            sub_caste_full.getJSONObject(sub_caste)
//                                                        sub_caste_name.add(sub_caste_data["sub_caste"].toString())
//                                                        sub_caste_id.add(sub_caste_data["id"].toString().replace(".0","").toInt())
//                                                    }
//                                                    val selectedsubcaste =
//                                                        subCasteSpinner1.selectedItem.toString()
//                                                    val position =
//                                                        sub_caste_name.indexOf(selectedsubcaste)
//                                                    var sub_caste_id_server =
//                                                        sub_caste_id.get(position)
//
//                                                if(AgreeOnReligionSpinner.selectedItem != "Select" ) {
//                                                    if (VisitingTempleSpinner.selectedItem != "Select") {
//                                                        val selecteddeepreligion = AgreeOnReligionSpinner.selectedItem.toString()
//                                                        val deeppos1 = deepreligion_name.indexOf(selecteddeepreligion)
//                                                        val deepreligion_id_server = deepreligion_id.get(deeppos1)
//
//                                                        val selectedworship = VisitingTempleSpinner.selectedItem.toString()
//                                                        val worshippos1 = worship_name.indexOf(selectedworship)
//                                                        val worship_id_server = worship_id.get(worshippos1)
//                                                        viewModel1.religiosInfoSubmitLiveData(
//                                                            body,
//                                                            religion_id_server,
//                                                            caste_id_server,
//                                                            deepreligion_id_server,
//                                                            worship_id_server,
//                                                            PrefsHelper().getPref<String>("userKey").toInt(),
//                                                            selectedreligion.replace("\"",""),
//                                                            selectedcaste.replace("\"",""),
//                                                            selectedsubcaste.replace("\"",""),
//                                                            selectedworship.replace("\"",""),
//                                                            selecteddeepreligion.replace("\"","")
//                                                        )
//                                                    }else{
//                                                        Toast.makeText(
//                                                            this,
//                                                            "Please mention how often you visit your place of worship",
//                                                            Toast.LENGTH_SHORT).show()
//                                                    }
//                                                }else{
//                                                    Toast.makeText(
//                                                        this,
//                                                        "Please mention how deeply religious you are",
//                                                        Toast.LENGTH_SHORT).show()
//                                                }
//
//                                                } else {
//                                                    Toast.makeText(
//                                                        this,
//                                                        "Please select your sub-caste",
//                                                        Toast.LENGTH_SHORT
//                                                    ).show()
//                                                }
//                                            } else { //no sub caste
//                                                if(AgreeOnReligionSpinner.selectedItem != "Select" ) {
//                                                    if (VisitingTempleSpinner.selectedItem != "Select") {
//                                                        val selecteddeepreligion = AgreeOnReligionSpinner.selectedItem.toString()
//                                                        val deeppos1 = deepreligion_name.indexOf(selecteddeepreligion)
//                                                        val deepreligion_id_server = deepreligion_id.get(deeppos1)
//
//                                                        val selectedworship = VisitingTempleSpinner.selectedItem.toString()
//                                                        val worshippos1 = worship_name.indexOf(selectedworship)
//                                                        val worship_id_server = worship_id.get(worshippos1)
//                                                        viewModel1.religiosInfoSubmitLiveData(
//                                                    body,
//                                                    religion_id_server,
//                                                    caste_id_server,
//                                                    deepreligion_id_server,
//                                                    worship_id_server,
//                                                    PrefsHelper().getPref<String>("userKey").toInt(),
//                                                    selectedreligion.replace("\"",""),
//                                                    selectedcaste.replace("\"",""),
//                                                    null,
//                                                     selectedworship.replace("\"",""),
//                                                     selecteddeepreligion.replace("\"","")
//                                                )
//                                                    }else{
//                                                        Toast.makeText(
//                                                            this,
//                                                            "Please mention how often you visit your place of worship",
//                                                            Toast.LENGTH_SHORT).show()
//                                                    }
//                                                }else{
//                                                    Toast.makeText(
//                                                        this,
//                                                        "Please mention how deeply religious you are",
//                                                        Toast.LENGTH_SHORT).show()
//                                                }
//                                            }
//                                        }
//                                    }
//                                } else {
//                                    Toast.makeText(
//                                        this,
//                                        "Please select your caste",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            } else {   //not caste
//                                        if(AgreeOnReligionSpinner.selectedItem != "Select" ) {
//                                            if (VisitingTempleSpinner.selectedItem != "Select") {
//                                                val selecteddeepreligion = AgreeOnReligionSpinner.selectedItem.toString()
//                                                val deeppos1 = deepreligion_name.indexOf(selecteddeepreligion)
//                                                val deepreligion_id_server = deepreligion_id.get(deeppos1)
//
//                                                val selectedworship = VisitingTempleSpinner.selectedItem.toString()
//                                                val worshippos1 = worship_name.indexOf(selectedworship)
//                                                val worship_id_server = worship_id.get(worshippos1)
//                                viewModel1.religiosInfoSubmitLiveData(
//                                    body,
//                                    religion_id_server,
//                                    0,
//                                    deepreligion_id_server,
//                                    worship_id_server,
//                                    PrefsHelper().getPref<String>("userKey").toInt(),
//                                    selectedreligion.replace("\"",""),
//                                    null,
//                                    null,
//                                    selectedworship.replace("\"",""),
//                                    selecteddeepreligion.replace("\"","")
//                                )
//                                            }else{
//                                                Toast.makeText(
//                                                    this,
//                                                    "Please mention how often you visit your place of worship",
//                                                    Toast.LENGTH_SHORT).show()
//                                            }
//                                        }else{
//                                            Toast.makeText(
//                                                this,
//                                                "Please mention how deeply religious you are",
//                                                Toast.LENGTH_SHORT).show()
//                                        }
//                            }
//
//                        }
//                    }
//                }
//            else{
//                Toast.makeText(this, "Please select your religion", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    @RequiresApi(Build.VERSION_CODES.KITKAT)
//    private fun pickFromDevice() {
//        //Create an Intent with action as ACTION_PICK
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "application/pdf"
//        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
//        val mimeTypes =
//            arrayOf("application/pdf")
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//        // Launching the Intent
//        startActivityForResult(intent, STORAGE_REQUEST_CODE)
//    }
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    @RequiresApi(Build.VERSION_CODES.KITKAT)
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == Activity.RESULT_OK) when (requestCode){
//            STORAGE_REQUEST_CODE -> {
//                //data.getData return the content URI for the selected Image
//                val selectedPdf: Uri? = data?.data
//                val pathHolder: String? = FilePath.getPath(this, selectedPdf)
//                pathHolder?.let { uploadFile(it) }
//                val filename = FilePath.getFileName(this, selectedPdf)
//                Toast.makeText(this, filename, Toast.LENGTH_SHORT).show()
//                UploadHoroscopeEditText?.setText(filename)
//            }
//        }
//    }
//
//    private fun uploadFile(pdfDecodableString: String) {
//        val requestFile = RequestBody.create(
//            MediaType.parse("multipart/form-data"),
//            pdfDecodableString
//        )
//        // MultipartBody.Part is used to send also the actual file name
//        body =
//            MultipartBody.Part.createFormData("horoscope", pdfDecodableString, requestFile)
//    }
//
//    private fun setupObserversSubmit() {
//        viewModel1.religiousInfoSubmitResponseLiveData.observe(this, Observer {
//            if(it!=null){
//                if( it.message=="Success"){
//
//                    Toast.makeText(this,"Religious Info updated successfully", Toast.LENGTH_SHORT).show()
//                    viewModel2.familyInfoLiveData()
//
//
//                }
//                else{
//                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//        //observe API call status
//        viewModel1.religiousInfoSubmitApiCallStatus.observe(this, Observer {
//            processStatus(it)
//        })
//
//    }
//
//    private fun setupobserversFamilyinfo(){
//        viewModel2.familyInfoResponseLiveData.observe(this, Observer {
//            if(it!=null){
//                if(it.message=="Success"){
//
//
//
//                    val intent = Intent(this, family_info::class.java)
//                    val response = gson.toJson(it)
//                    intent.putExtra("response",response)
//                    PrefsHelper().savePref("response",response)
//                    PrefsHelper().savePref("Account_Status",5)
//                    startActivity(intent)
//                }
//                else{
//                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//        viewModel2.familyInfoApiCallStatus.observe(this, Observer {
//            processStatus(it)
//        })
//    }
//
//    private fun main_code(jsonArray: String) {
//        val object_main1 = JSONObject(jsonArray)
//        val object_main = object_main1.getJSONObject("choice")
//        val obj_main = object_main1.getJSONObject("user_choice")
//
//        val array = object_main.getJSONArray("religions")
//        val  list_religion_name:MutableList<String> = arrayListOf()
////obj_main["religion"].toString().equals("null")
//        if(obj_main.has("religion")) {
//            if ((obj_main["religion"].toString().replace(".0", "").toInt() < 1)) {
//                list_religion_name.add("Select")
//                for (i in 0 until array.length()) {
//
//                    val obj = array.getJSONObject(i)
//                    if (obj.getString("religion") != "- Select -")
//                    list_religion_name.add(obj.getString("religion"))
////                list_religion.add(obj.getInt("id"))
//                }
//
//            } else {
//                val selected_religion = obj_main["religion_name"]
//                val selected_religion1 =
//                    selected_religion.toString().replace("'", "").replace("\"", "")
//
//                list_religion_name.add(selected_religion1)
//                for (i in 0 until array.length()) {
//                    val obj = array.getJSONObject(i)
//                    if (obj.getString("religion") != selected_religion1 &&(obj.getString("religion") != "- Select -")) {
//                        list_religion_name.add(obj.getString("religion"))
//                    }
//                }
//            }
//        }
//        else {
//            list_religion_name.add("Select")
//            for (i in 0 until array.length()) {
//                val obj = array.getJSONObject(i)
//                if (obj.getString("religion") != "- Select -")
//                list_religion_name.add(obj.getString("religion"))
////                list_religion.add(obj.getInt("id"))
//            }
//        }
//
//        val adapter_religion = ArrayAdapter(
//            this,
//            R.layout.color_spinner_layout,
//            list_religion_name
//        )
//        adapter_religion.setDropDownViewResource(R.layout.spinner_dropdown_layout)
//        religionSpinner.setAdapter(adapter_religion)
//
//    }
//
//    private fun deeply_religious() {
//        val object_main1 = JSONObject(jsonArray)
//        val object_main = object_main1.getJSONObject("choice")
//        val array = object_main.getJSONArray("deeply_religious")
//        val list_deeply_religious:MutableList<String> = arrayListOf()
//        val obj_main = object_main1.getJSONObject("user_choice")
//
//// need to change it as 0
//        if(obj_main.has("deeply_religious")){
//            val deep_array = object_main.getJSONArray("deeply_religious")
//            val deepreligion_name: MutableList<String> = arrayListOf()
//            val deepreligion_id: MutableList<Int> = arrayListOf()
//            for (i in 0 until deep_array.length()) {
//                val deeptemp = deep_array.getJSONObject(i)
//                deepreligion_name.add(deeptemp["deeply_religious"].toString())
//                deepreligion_id.add(deeptemp["id"].toString().toInt())
//            }
//
//            val selecteddeepreligion = obj_main["deeply_religious"].toString().replace(".0","").toInt()
//            val deeppos1 = deepreligion_id.indexOf(selecteddeepreligion)
//            val deeply_religious_text = deepreligion_name.get(deeppos1)
//
//            list_deeply_religious.add(deeply_religious_text)
//            for (i in 0 until array.length()) {
//                if ((obj_main["deeply_religious"].toString().replace(".0","").toInt() < 1)) {
//                    list_deeply_religious.add("Select")
//                    for (i in 0 until array.length()) {
//                        val obj = array.getJSONObject(i)
//                        list_deeply_religious.add(obj.getString("deeply_religious"))
//
//                    }
//                }
//                    else{
//                val obj = array.getJSONObject(i)
//                if(obj.getString("deeply_religious") != deeply_religious_text) {
//                    list_deeply_religious.add(obj.getString("deeply_religious"))
//                }
//                }
//            }
//
//        }
//
//        else{
//            list_deeply_religious.add("Select")
//            for (i in 0 until array.length()) {
//                val obj = array.getJSONObject(i)
//                list_deeply_religious.add(obj.getString("deeply_religious"))
//            }
//
//
//        }
//
//
//
//        val adapter_deeply_religious = ArrayAdapter(
//            this,
//            R.layout.color_spinner_layout,
//            list_deeply_religious
//        )
//        adapter_deeply_religious.setDropDownViewResource(R.layout.spinner_dropdown_layout)
//        AgreeOnReligionSpinner.setAdapter(adapter_deeply_religious)
//    }
//
//    private fun visiting_temple() {
//            val object_main1 = JSONObject(jsonArray)
//            val object_main = object_main1.getJSONObject("choice")
//            val array = object_main.getJSONArray("worship_place_visit")
//            val list_worship:MutableList<String> = arrayListOf()
//        val obj_main = object_main1.getJSONObject("user_choice")
//
//        if(obj_main.has("worship_place_visit")) {
//            if ((obj_main["worship_place_visit"].toString().replace(".0", "").toInt() < 1)) {
//                list_worship.add("Select")
//                for (i in 0 until array.length()) {
//                    val obj = array.getJSONObject(i)
//                    list_worship.add(obj.getString("worship_place_visit"))
//                }
//            } else {
//                val worship_array = object_main.getJSONArray("worship_place_visit")
//                val worship_name: MutableList<String> = arrayListOf()
//                val worship_id: MutableList<Int> = arrayListOf()
//                for (i in 0 until worship_array.length()) {
//                    val worshiptemp = worship_array.getJSONObject(i)
//                    worship_name.add(worshiptemp["worship_place_visit"].toString())
//                    worship_id.add(worshiptemp["id"].toString().toInt())
//                }
//                val selectedworship =
//                    obj_main["worship_place_visit"].toString().replace(".0", "").toInt()
//                val worshippos1 = worship_id.indexOf(selectedworship)
//                val temple_visit_text = worship_name.get(worshippos1)
//                list_worship.add(temple_visit_text)
//                for (i in 0 until array.length()) {
//                    val obj = array.getJSONObject(i)
//                    if (obj.getString("worship_place_visit") != temple_visit_text) {
//                        list_worship.add(obj.getString("worship_place_visit"))
//                    }
//                }
//            }
//        }
//
//        else {
//            list_worship.add("Select")
//            for (i in 0 until array.length()) {
//                val obj = array.getJSONObject(i)
//                list_worship.add(obj.getString("worship_place_visit"))
//            }
//        }
//
//
//
//
//
//            val adapter_worship = ArrayAdapter(
//                this,
//                R.layout.color_spinner_layout,
//                list_worship
//            )
//            adapter_worship.setDropDownViewResource(R.layout.spinner_dropdown_layout)
//            VisitingTempleSpinner.setAdapter(adapter_worship)
//        }
//
//
//
//    private fun caste_code() {
//
//        if (religionSpinner.selectedItem != "Select") {
//            if (jsonArray != "") {
//                val object_main1 = JSONObject(jsonArray)
//                val object_main = object_main1.getJSONObject("choice")
//                val obj_main = object_main1.getJSONObject("user_choice")
//                val array = object_main.getJSONArray("religions")
//                var selected_religion = religionSpinner.selectedItem
//                val caste_name_array = get_data_for_selected_religion(selected_religion,array)
//                val  list_caste_name:MutableList<String> = arrayListOf()
//                if(obj_main.has("caste")) {
//                    val selected_caste = obj_main["caste"]
//                    val selected_caste1 = selected_caste.toString().replace(".0", "")
//                    val selected_caste_id = selected_caste1.toInt()
//                    if (selected_caste_id == 0) {
//
//                        if (caste_name_array.size > 0) {
//                            list_caste_name.add("Select")
//                            for (i in 0 until caste_name_array.size) {
//                                if(caste_name_array[i] != "- Select -")
//                                list_caste_name.add(caste_name_array[i])
//                            }
//                        }
//                    } else {
//                        val selected_caste_name =
//                            obj_main["caste_name"].toString().replace("'", "").replace("\"", "")
//                        if (caste_name_array.size > 0) {
//                            if (selected_caste_name in caste_name_array) {
//                                list_caste_name.add(selected_caste_name)
//                            } else {
//                                list_caste_name.add("Select")
//                            }
//                            for (i in 0 until caste_name_array.size) {
//                                if (selected_caste_name != caste_name_array[i] && caste_name_array[i] != "- Select -") {
//                                    list_caste_name.add(caste_name_array[i])
//                                }
//                            }
//                        } else {
//                //                        Toast.makeText(this,caste_name_array.size.toString(), Toast.LENGTH_SHORT).show()
//                //                        list_caste_name.add("Select")
//                        }
//                    }
//                }
//                else{
//                    if(caste_name_array.size > 0) {
//                        list_caste_name.add("Select")
//                        for (i in 0 until caste_name_array.size) {
//                            if(caste_name_array[i] != "- Select -")
//                            list_caste_name.add(caste_name_array[i])
//                        }
//                    }
//                }
//
//                val adapter_caste = ArrayAdapter(
//                    this,
//                    R.layout.color_spinner_layout,
//                    list_caste_name
//                )
//                adapter_caste.setDropDownViewResource(R.layout.spinner_dropdown_layout)
//                casteSpinner.setAdapter(adapter_caste)
//            }
//        }else{
//
//            val adapter_caste = ArrayAdapter.createFromResource(
//                this,
//                R.array.caste_array,
//                R.layout.color_spinner_layout
//            )
//            adapter_caste.setDropDownViewResource(R.layout.spinner_dropdown_layout)
//            casteSpinner.setAdapter(adapter_caste)
//
//
//            val adapter_sub_caste = ArrayAdapter.createFromResource(
//                this,
//                R.array.sub_caste_array,
//                R.layout.color_spinner_layout
//            )
//            adapter_sub_caste.setDropDownViewResource(R.layout.spinner_dropdown_layout)
//            subCasteSpinner1.setAdapter(adapter_sub_caste)
//        }
//
//    }
////
//    private fun get_data_for_selected_religion(
//        selectedReligion: Any?,
//        array: JSONArray
//        ): List<String> {
//        val caste_name:MutableList<String> = arrayListOf()
//        for (i in 0 until array.length()){
//            var religionData = array.getJSONObject(i)
//            if (religionData["religion"] == selectedReligion.toString()){
//                 var caste_data = religionData.getJSONArray("caste")
//                for (j in 0 until caste_data.length()) {
//                    var each_caste_element = caste_data.getJSONObject(j)
//                    caste_name.add(each_caste_element["caste"].toString())
//                }
//            }
//        }
//        return caste_name.toList()
//    }
//
//    private fun subcaste_code() {
//        if (casteSpinner.selectedItem != "Select") {
//            if (jsonArray != "") {
//                val object_main1 = JSONObject(jsonArray)
//                val object_main = object_main1.getJSONObject("choice")
//                val array = object_main.getJSONArray("religions")
//
////                val obj = array.getJSONObject(religionSpinner.selectedItemId.toInt())
////                val subCasteobj =
////                    obj.getJSONArray("caste").getJSONObject(casteSpinner.selectedItemId.toInt())
//
//
//
//                val obj_main = object_main1.getJSONObject("user_choice")
//
//                val  list_sub_caste_name:MutableList<String> = arrayListOf()
//                for (i in 0 until array.length()){
//                    val temp = array.getJSONObject(i)
//                    if(temp["religion"]==religionSpinner.selectedItem)
//                    {
//                        val caste_array = temp.getJSONArray("caste")
//                        if (caste_array.length() > 0){
//                            for(j in 0 until caste_array.length()){
//                                val temp1 = caste_array.getJSONObject(j)
//                                if(temp1["caste"]==casteSpinner.selectedItem){
//                                    val sub_caste_full = temp1.getJSONArray("sub_caste")
//                                    if(sub_caste_full.length()>0){
//                                        val sub_caste_name:MutableList<String> = arrayListOf()
//                                        for(sub_caste in 0 until sub_caste_full.length()){
//                                            val sub_caste_data = sub_caste_full.getJSONObject(sub_caste)
//                                            sub_caste_name.add(sub_caste_data["sub_caste"].toString())
//                                        }
//
//
//                                        if(obj_main.has("sub_caste")) {
//                                            val previously_selected_caste = obj_main["sub_caste"]
//                                            val previously_selected_caste1 = previously_selected_caste.toString().replace(".0","")
//                                            val previously_selected_caste_id = previously_selected_caste1.toInt()
//                                            if (previously_selected_caste_id > 0) {
//                                                val previously_selected_sub_caste_name =
//                                                    obj_main["sub_caste_name"].toString()
//                                                        .replace("'", "").replace("\"", "")
//                                                if (sub_caste_name.size > 0) {
//                                                    if (previously_selected_sub_caste_name in sub_caste_name) {
//                                                        list_sub_caste_name.add(
//                                                            previously_selected_sub_caste_name
//                                                        )
//                                                    } else {
//                                                        list_sub_caste_name.add("Select")
//                                                    }
//
//                                                    for (elem in 0 until sub_caste_name.size) {
//                                                        if (previously_selected_sub_caste_name != sub_caste_name[elem]) {
//                                                            list_sub_caste_name.add(sub_caste_name[elem])
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                            else{
//                                                list_sub_caste_name.add("Select")
//                                                if(sub_caste_name.size > 0) {
//                                                    for (elem in 0 until sub_caste_name.size) {
//                                                        list_sub_caste_name.add(sub_caste_name[elem])
//                                                    }
//                                                }
//                                            }
//                                        }
//                                        else{
//                                            list_sub_caste_name.add("Select")
//                                            if(sub_caste_name.size > 0) {
//                                                for (elem in 0 until sub_caste_name.size) {
//                                                    list_sub_caste_name.add(sub_caste_name[elem])
//                                                }
//                                            }
//
//                                        }
//                                    }
//                                    else{
//                                        Toast.makeText(this,"No sub caste for the caste you have selected", Toast.LENGTH_SHORT).show()
//                                    }
//
//                                }
//                            }
//                        }
//                        else{
//                            Toast.makeText(this,"No caste and sub caste for the religion you have selected", Toast.LENGTH_SHORT).show()
//
//                        }
//                    }
//                }
//
//
//
////                val selected_religion = religionSpinner.selectedItem
////                val caste_name_array = get_data_for_selected_religion(selected_religion,array)
////                val selected_caste = casteSpinner.selectedItem
////                val sub_caste_name_array = get_data_for_selected_religion(selected_caste,array)
////
////                val array_sub_caste: Array<String> =
//                    resources.getStringArray(R.array.sub_caste_array)
////                val list_sub_caste = array_sub_caste.toMutableList()
////
////                for (i in 0 until subCasteobj.getJSONArray("sub_caste").length()) {
////                    list_sub_caste.add(
////                        subCasteobj.getJSONArray("sub_caste").getJSONObject(i).getString(
////                            "sub_caste"
////                        )
////                    )
////                }
//
//                val adapter_sub_caste = ArrayAdapter(
//                    this,
//                    R.layout.color_spinner_layout,
//                    list_sub_caste_name
//                )
//                adapter_sub_caste.setDropDownViewResource(R.layout.spinner_dropdown_layout)
//                subCasteSpinner1.setAdapter(adapter_sub_caste)
//            }
//        }else{
//            val adapter_sub_caste = ArrayAdapter.createFromResource(
//                this,
//                R.array.sub_caste_array,
//                R.layout.color_spinner_layout
//            )
//            adapter_sub_caste.setDropDownViewResource(R.layout.spinner_dropdown_layout)
//            subCasteSpinner1.setAdapter(adapter_sub_caste)
//        }
//    }
//
//
//    private fun processStatus(resource: ResourceStatus) {
//
//        when (resource.status) {
//            StatusType.SUCCESS -> {
//                dismissDialog()
//            }
//            StatusType.EMPTY_RESPONSE -> {
//                dismissDialog()
//            }
//            StatusType.PROGRESSING -> {
//                showDialog()
//            }
//            StatusType.SWIPE_RELOADING -> {
//            }
//            StatusType.ERROR -> {
//                CommonUtils().showSnackbar(SaveReligiousInfobutton.rootView, "Please try again")
//                dismissDialog()
//            }
//            StatusType.LOADING_MORE -> {
//                // CommonUtils().showSnackbar(binding.root, "Loading more..")
//            }
//            StatusType.NO_NETWORK -> {
//                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
//            }
//            StatusType.SESSION_EXPIRED -> {
//                CommonUtils().showSnackbar(SaveReligiousInfobutton.rootView, "session expired")
//            }
//        }
//    }
//
//    override fun onNothingSelected(parent: AdapterView<*>?) {
//    }
//
//    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        caste_code()
//        subcaste_code()
//    }
//
//    override fun onBackPressed() {
//
//        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
//        if (prefs.contains("Reference")){
//            if (PrefsHelper().getPref<Int>("Reference")!=8) {
//
//            }
//            else{
//                super.onBackPressed()
//            }
//        }
//        else{
//
//        }
//
    }
//
//
//
//}
//
//
