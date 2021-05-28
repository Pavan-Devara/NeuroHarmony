package com.neuro.neuroharmony.ui.login.SocialPreferences

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abdeveloper.library.MultiSelectDialog
import com.abdeveloper.library.MultiSelectModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.SocialFilter.SocialFilterResponse
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.ui.login.SocialProfile.ReligionViewModel
import kotlinx.android.synthetic.main.activity_social_preference.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.roundToInt


class SocialPreference : BaseActivity(), AdapterView.OnItemSelectedListener{

    lateinit var viewModel: ReligionViewModel
    lateinit var viewModelSocialFilter: SocialFiltersViewModel

    var jsonArray = ""
    var gson = Gson()

    var id_array_religion: MutableList<Int> = arrayListOf()
    var id_array_caste: MutableList<Int> = arrayListOf()
    var id_array_profession: MutableList<Any> = arrayListOf()
    var id_religion_for_caste: MutableList<Int> = arrayListOf()

    var name_array_religion: MutableList<Any> = arrayListOf()
    var name_array_caste: MutableList<Any> = arrayListOf()
    var name_array_profession: MutableList<Any> = arrayListOf()

    var list_profession_name:MutableList<String> = arrayListOf()

    var list_profession_id: MutableList<Any> = arrayListOf()

    var social_filter_active = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MaterialTheme)
        setContentView(R.layout.activity_social_preference)

        viewModel = ViewModelProviders.of(this)[ReligionViewModel::class.java]
        viewModelSocialFilter = ViewModelProviders.of(this)[SocialFiltersViewModel::class.java]
        viewModelSocialFilter.socialfiltervm()
        setupObserversReligiousData()
        setupObserversCasteData()
        setupObserversFilterData()

        social_filter_disabled.setOnClickListener {
            social_filter_active = true
            social_filter_enabled.visibility = View.VISIBLE
            social_filter_disabled.visibility = View.GONE
            religion_socialfilter_subheading.visibility = View.VISIBLE
            relative_religion_social_filter.visibility = View.VISIBLE
            chipGroupReligion.visibility = View.VISIBLE
            caste_socialfilter_subheading.visibility = View.VISIBLE
            relative_caste_social_filter.visibility = View.VISIBLE
            chipGroupCaste.visibility = View.VISIBLE
            age_socialfilter_subheading.visibility = View.VISIBLE
            heightfilterLinearLayout.visibility = View.VISIBLE
            agefilterLinearLayout.visibility = View.VISIBLE
            height_socialfilter_subheading.visibility = View.VISIBLE
            relative_profession_social_filter.visibility = View.VISIBLE
            profession_socialfilter_subheading.visibility = View.VISIBLE
            chipGroupProfession.visibility = View.VISIBLE
        }

        social_filter_enabled.setOnClickListener{
            social_filter_active = false
            social_filter_enabled.visibility = View.GONE
            social_filter_disabled.visibility = View.VISIBLE
            religion_socialfilter_subheading.visibility = View.GONE
            relative_religion_social_filter.visibility = View.GONE
            chipGroupReligion.visibility = View.GONE
            caste_socialfilter_subheading.visibility = View.GONE
            relative_caste_social_filter.visibility = View.GONE
            chipGroupCaste.visibility = View.GONE
            age_socialfilter_subheading.visibility = View.GONE
            heightfilterLinearLayout.visibility = View.GONE
            agefilterLinearLayout.visibility = View.GONE
            height_socialfilter_subheading.visibility = View.GONE
            relative_profession_social_filter.visibility = View.GONE
            profession_socialfilter_subheading.visibility = View.GONE
            chipGroupProfession.visibility = View.GONE
        }


        religionSocialFilterSpinner.setOnClickListener{
            viewModel.ReligionInfoLiveData()
        }

        casteSocialFilterSpinner.setOnClickListener{
            if (id_array_religion.isNotEmpty()) {
                viewModel.CasteInfoLiveData(id_array_religion as ArrayList<Int>)
            }else{
                Toast.makeText(this, "Please select a religion first", Toast.LENGTH_SHORT).show()
            }
        }


        professionSocialFilterSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position>0) {
                    var add_chip = 1
                    val chg = chipGroupProfession
                    val chipsCount = chg.childCount
                    if (chipsCount > 0) {
                        for (i in 0 until chipsCount) {
                            val chip = chg.getChildAt(i) as Chip
                            var selected_profession = chip.text.toString()
                            if (selected_profession == professionSocialFilterSpinner?.selectedItem.toString()) {
                                add_chip = 0
                            }
                        }
                    }
                    if (add_chip==1) {
                        profession_socialfilter_number_selected.text = (chipsCount+1).toString()+" Selected"
                        professionSocialFilterSpinner?.selectedItem?.let {
                            selection(
                                it,
                                chipGroupProfession
                            )
                        }
                        name_array_profession.add(professionSocialFilterSpinner.selectedItem)
                        val pos_id =
                            list_profession_name.indexOf(professionSocialFilterSpinner.selectedItem)
                        val id_profession = list_profession_id[pos_id]
                        id_array_profession.add(id_profession)
                    }
                    professionSocialFilterSpinner.setSelection(0)
                }
            }
        }

        saveSocialPreferences?.setOnClickListener {
            setupListenerSubmit()
        }

        setupObserversSubmit()
    }


    private fun setupListenerSubmit() {
        val caste_list: MutableList<Any> = arrayListOf()
        for (i in 0 until id_array_caste.size){
            val caste_dict = mutableMapOf("id" to id_array_caste[i],
                "caste" to name_array_caste[i], "religion" to id_religion_for_caste[i])
            caste_list.add(caste_dict)
        }

        val religion_list: MutableList<Any> = arrayListOf()
        for (i in 0 until id_array_religion.size){
            val religion_dict = mutableMapOf("id" to id_array_religion[i],
                "religion" to name_array_religion[i])
            religion_list.add(religion_dict)
        }

        val profession_list: MutableList<Any> = arrayListOf()
        for (i in 0 until id_array_profession.size){
            val profession_dict = mutableMapOf("id" to id_array_profession[i],
                "choice" to name_array_profession[i])
            profession_list.add(profession_dict)
        }


        if (ageMaxSocialFilter?.selectedItem!=null || ageMinSocialFilter?.selectedItem!=null ||
            heightMaxSocialFilter?.selectedItem!=null || heightMinSocialFilter?.selectedItem!=null) {
            var max_age = ageMaxSocialFilter.selectedItem.toString().toInt()
            var max_height = heightMaxSocialFilter.selectedItem.toString().toInt()
            var min_age = ageMinSocialFilter.selectedItem.toString().toInt()
            var min_height = heightMinSocialFilter.selectedItem.toString().toInt()


            if (max_age>=min_age){
                if(max_height>=min_height) {
                    viewModelSocialFilter.socialfiltersubmitvm(
                        caste_list,
                        max_age,
                        max_height,
                        min_age,
                        min_height,
                        profession_list,
                        religion_list,
                        social_filter_active
                    )
                }else {
                    Toast.makeText(this, "Please select valid min and max height", Toast.LENGTH_SHORT)
                        .show()
                }
            }else {
                Toast.makeText(this, "Please select valid min and max age", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }


    private fun remove_caste_of_religion(removed_religion: String) {
        val religion_to_remove = name_array_religion.indexOf(removed_religion)
        val religion_to_remove_id = id_array_religion[religion_to_remove]
        val castes_to_remove: MutableList<Int> = arrayListOf()
        val chg = chipGroupCaste
        val count = chg.childCount
        for (i in 0 until count){
            val chip = chg.getChildAt(i) as Chip
            val caste = name_array_caste.indexOf(chip.text.toString())
            val id_caste_to_remove_religion = id_religion_for_caste[caste]
            if (id_caste_to_remove_religion==religion_to_remove_id){
                castes_to_remove.add(caste)
            }
        }


        for (i in castes_to_remove.size-1 downTo 0){
            id_religion_for_caste.removeAt(castes_to_remove[i])
            id_array_caste.removeAt(castes_to_remove[i])
            name_array_caste.removeAt(castes_to_remove[i])
        }

        chipGroupCaste.removeAllViews()

        for (i in 0 until name_array_caste.size){
            selection(name_array_caste[i].toString(), chipGroupCaste)
        }
        caste_socialfilter_number_selected?.text = name_array_caste.size.toString() + " Selected"
    }


    private fun get_data_for_selected_religion(
        selectedReligion: Any?,
        array: JSONArray
    ): List<MutableList<out Any>> {
        val caste_name:MutableList<String> = arrayListOf()
        val caste_id:MutableList<Any> = arrayListOf()
        val religion_of_caste:MutableList<Any> = arrayListOf()
        val caste:List<MutableList<out Any>> = listOf(caste_id, caste_name, religion_of_caste)
        for (i in 0 until array.length()){
            var religionData = array.getJSONObject(i)
            if (religionData["religion"] == selectedReligion.toString()){
                var caste_data = religionData.getJSONArray("caste")
                for (j in 0 until caste_data.length()) {
                    var each_caste_element = caste_data.getJSONObject(j)
                    caste_name.add(each_caste_element["caste"].toString())
                    caste_id.add(each_caste_element["id"])
                    selectedReligion?.let { religion_of_caste.add(it) }
                }
            }
        }
        return caste
    }


    fun selection(
        selectedItem: Any,
        chipGroup: ChipGroup
    ) {
        val chip = Chip(this)
        val drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Entry)

        chip.setChipDrawable(drawable)
        chip.chipBackgroundColor = resources.getColorStateList(R.color.chip_color_state_list)
        chip.setTextColor(resources.getColor(R.color.white))
        chip.chipStrokeColor = resources.getColorStateList(R.color.chip_stroke_color_state_list)
        chip.chipStrokeWidth = 1f
        chip.closeIconTint = resources.getColorStateList(R.color.chip_stroke_color_state_list)
        chip.visibility = View.VISIBLE
        chip.isCheckable = false
        chip.isClickable = false
        chip.setPadding(100, 0, 100,0)
        chip.text = selectedItem.toString()
        chip.setOnCloseIconClickListener{
            if (chipGroup==chipGroupReligion){
                remove_caste_of_religion(chip.text.toString())
                val chg = chipGroupReligion
                val chipsCount = chg.childCount
                religion_socialfilter_number_selected.text = (chipsCount-1).toString()+" Selected"
                val remove = name_array_religion.indexOf(chip.text.toString())
                id_array_religion.removeAt(remove)
                name_array_religion.remove(chip.text.toString())
            }else if (chipGroup==chipGroupCaste){
                val chg = chipGroupCaste
                val chipsCount = chg.childCount
                caste_socialfilter_number_selected.text = (chipsCount-1).toString()+" Selected"
                val remove = name_array_caste.indexOf(chip.text.toString())
                id_array_caste.removeAt(remove)
                id_religion_for_caste.removeAt(remove)
                name_array_caste.remove(chip.text.toString())
            }else if (chipGroup==chipGroupProfession){
                val chg = chipGroupProfession
                val chipsCount = chg.childCount
                profession_socialfilter_number_selected.text = (chipsCount-1).toString()+" Selected"
                val remove = name_array_profession.indexOf(chip.text.toString())
                id_array_profession.removeAt(remove)
                name_array_profession.remove(chip.text.toString())
            }

            chipGroup?.removeView(chip)
        }
        chipGroup?.addView(chip)
    }


    private fun filter_main(it: SocialFilterResponse) {

        if (it.data.user_choice.social_filter_active){
            social_filter_active = true
            social_filter_enabled.visibility = View.VISIBLE
            social_filter_disabled.visibility = View.GONE
            religion_socialfilter_subheading.visibility = View.VISIBLE
            relative_religion_social_filter.visibility = View.VISIBLE
            chipGroupReligion.visibility = View.VISIBLE
            caste_socialfilter_subheading.visibility = View.VISIBLE
            relative_caste_social_filter.visibility = View.VISIBLE
            chipGroupCaste.visibility = View.VISIBLE
            age_socialfilter_subheading.visibility = View.VISIBLE
            heightfilterLinearLayout.visibility = View.VISIBLE
            agefilterLinearLayout.visibility = View.VISIBLE
            height_socialfilter_subheading.visibility = View.VISIBLE
            relative_profession_social_filter.visibility = View.VISIBLE
            profession_socialfilter_subheading.visibility = View.VISIBLE
            chipGroupProfession.visibility = View.VISIBLE
        }else{
            social_filter_active = false
            social_filter_disabled.visibility = View.VISIBLE
            social_filter_enabled.visibility = View.GONE
            religion_socialfilter_subheading.visibility = View.GONE
            relative_religion_social_filter.visibility = View.GONE
            chipGroupReligion.visibility = View.GONE
            caste_socialfilter_subheading.visibility = View.GONE
            relative_caste_social_filter.visibility = View.GONE
            chipGroupCaste.visibility = View.GONE
            age_socialfilter_subheading.visibility = View.GONE
            heightfilterLinearLayout.visibility = View.GONE
            agefilterLinearLayout.visibility = View.GONE
            height_socialfilter_subheading.visibility = View.GONE
            relative_profession_social_filter.visibility = View.GONE
            profession_socialfilter_subheading.visibility = View.GONE
            chipGroupProfession.visibility = View.GONE
        }

        val age: MutableList<Int> = arrayListOf()
        for (i in 18 until 100){
            age.add(i)
        }
        val adapter_age = ArrayAdapter(
            this,
            R.layout.spinner_selected_social_filter_layout,
            age
        )
        adapter_age.setDropDownViewResource(R.layout.spinner_dropdown_social_filter_layout)
        ageMaxSocialFilter.setAdapter(adapter_age)
        ageMinSocialFilter.setAdapter(adapter_age)
        ageMaxSocialFilter.setSelection(99-18)

        val height: MutableList<Int> = arrayListOf()
        for (i in 100 until 221){
            height.add(i)
        }
        val adapter_height = ArrayAdapter(
            this,
            R.layout.spinner_selected_social_filter_layout,
            height
        )
        adapter_height.setDropDownViewResource(R.layout.spinner_dropdown_social_filter_layout)
        heightMaxSocialFilter.setAdapter(adapter_height)
        heightMinSocialFilter.setAdapter(adapter_height)
        heightMaxSocialFilter.setSelection(220-100)

        if (it.data.user_choice.max_age!=null){
            val age = it.data.user_choice.max_age.toString().replace(".0","")
            val new_age = age.toInt()
            ageMaxSocialFilter?.setSelection(new_age-18)
        }
        if (it.data.user_choice.min_age!=null){
            val age = it.data.user_choice.min_age.toString().replace(".0","")
            val new_age = age.toInt()
            ageMinSocialFilter?.setSelection(new_age-18)
        }
        if (it.data.user_choice.min_height!=null){
            val height = it.data.user_choice.min_height.toString().replace(".0","")
            val new_height = height.toInt()
            heightMinSocialFilter?.setSelection(new_height-100)
        }
        if (it.data.user_choice.max_height!=null){
            val height = it.data.user_choice.max_height.toString().replace(".0","")
            val new_height = height.toInt()
            heightMaxSocialFilter?.setSelection(new_height-100)
        }

        val jsonObject = gson.toJson(it.data)
        val gsonObject = JSONObject(jsonObject)
        val user_choice = gsonObject.getJSONObject("user_choice")

        if (user_choice.has("religion")){
            val religionArray = user_choice.getJSONArray("religion")
            religion_socialfilter_number_selected.text = (religionArray.length()).toString()+" Selected"
            for (i in 0 until religionArray.length()){
                if (user_choice.getJSONArray("religion").getJSONObject(i).has("religion")&&
                    user_choice.getJSONArray("religion").getJSONObject(i).has("id")) {
                    val id = religionArray.getJSONObject(i).get("id")
                    val name = religionArray.getJSONObject(i).get("religion")
                    name.let { selection(it, chipGroupReligion) }
                    name_array_religion.add(name)
                    id_array_religion.add(id.toString().toDouble().roundToInt())
                }
            }
        }

        if (user_choice.has("caste")){
            val casteArray = user_choice.getJSONArray("caste")
            caste_socialfilter_number_selected.text = (casteArray.length()).toString()+" Selected"
            for (i in 0 until casteArray.length()) {
                if (user_choice.getJSONArray("caste").getJSONObject(i).has("caste")&&
                    user_choice.getJSONArray("caste").getJSONObject(i).has("id") &&
                    user_choice.getJSONArray("caste").getJSONObject(i).has("religion")) {
                    val id = casteArray.getJSONObject(i).get("id")
                    val name = casteArray.getJSONObject(i).get("caste")
                    val religion = casteArray.getJSONObject(i).get("religion")
                    name.let { selection(it, chipGroupCaste) }
                    name_array_caste.add(name)
                    id_array_caste.add(id.toString().toDouble().roundToInt())
                    id_religion_for_caste.add(religion.toString().toDouble().roundToInt())
                }

            }
        }

        if (user_choice.has("profession")){
            val professionArray = user_choice.getJSONArray("profession")
            profession_socialfilter_number_selected.text = (professionArray.length()).toString()+" Selected"
            for (i in 0 until professionArray.length()) {
                if (user_choice.getJSONArray("profession").getJSONObject(i).has("choice")&&
                    user_choice.getJSONArray("profession").getJSONObject(i).has("id")) {
                    val id = professionArray.getJSONObject(i).get("id")
                    val name = professionArray.getJSONObject(i).get("choice")
                    name.let { selection(it, chipGroupProfession) }
                    name_array_profession.add(name)
                    id_array_profession.add(id)
                }
            }
        }


        val array = it.data.choice.profession
        list_profession_name.add("Select")
        list_profession_id.add(0)
        for (i in 0 until array.size) {
            val obj = array[i].choice
            list_profession_name.add(obj)
            list_profession_id.add(array[i].id)
        }

        val adapter_profession = ArrayAdapter(
            this,
            R.layout.color_spinner_layout,
            list_profession_name
        )
        adapter_profession.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        professionSocialFilterSpinner?.setAdapter(adapter_profession)


    }


    private fun setupObserversReligiousData() {
        viewModel.ReligionInfoResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    jsonArray = gson.toJson(it.data)

                    val listOfReligions= ArrayList<MultiSelectModel>()

                    val religions = mutableMapOf<Int, String>()
                    for (i in 0 until it.data.size) {
                        listOfReligions.add(MultiSelectModel(it.data[i].id,it.data[i].religion));
                        religions.put(it.data[i].id, it.data[i].religion)
                    }

                    var preselected = id_array_religion
                    preselected = preselected as ArrayList<Int>
                    val multiSelectDialogCountry =  MultiSelectDialog()
                        .title("Religions") //setting title for dialog
                        .titleSize(25.toFloat())
                        .positiveText("Done")
                        .negativeText("Cancel")
                        .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
                        .setMaxSelectionLimit(listOfReligions.size) //you can set maximum checkbox selection limit (Optional)
                        .multiSelectList(listOfReligions) // the multi select model list with ids and name
                        .preSelectIDsList(preselected)
                        .onSubmit(object : MultiSelectDialog.SubmitCallbackListener{
                            override fun onSelected(
                                p0: ArrayList<Int>,
                                p1: ArrayList<String>?,
                                p2: String?
                            ) {
                                id_array_religion = arrayListOf()
                                name_array_religion =  arrayListOf()
                                chipGroupReligion.removeAllViews()
                                for (i in 0 until p0.size) {
                                    if (p0[i] != 0) {
                                        id_array_religion.add(p0[i])
                                        religions[p0[i]]?.let { it1 -> name_array_religion.add(it1)}
                                        religions[p0[i]]?.let { it1 -> selection(it1, chipGroupReligion) }
                                    }
                                }
                                religion_socialfilter_number_selected?.text = p0.size.toString()+" Selected"
                            }

                            override fun onCancel() {

                            }
                        })
                        .show(getSupportFragmentManager(), "multiSelectDialog")

                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        //observe API call status
        viewModel.ReligionInfoApiCallStatus.observe(this, Observer {
            processStatus1(it)
        })

    }


    private fun setupObserversCasteData() {
        viewModel.CasteInfoResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    jsonArray = gson.toJson(it.data)

                    val listOfCastes= ArrayList<MultiSelectModel>()
                    val religionForCaste = mutableMapOf<Int, Int>()
                    val casteNameForCaste = mutableMapOf<Int, String>()
                    for (i in 0 until it.data.size) {
                        listOfCastes.add(MultiSelectModel(it.data[i].id,it.data[i].caste));
                        religionForCaste.put(it.data[i].id, it.data[i].religion)
                        casteNameForCaste.put(it.data[i].id, it.data[i].caste)
                    }

                    var preselected = id_array_caste
                    preselected = preselected as ArrayList<Int>

                    val multiSelectDialogCaste =  MultiSelectDialog()
                        .title("Castes") //setting title for dialog
                        .titleSize(25.toFloat())
                        .positiveText("Done")
                        .negativeText("Cancel")
                        .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
                        .setMaxSelectionLimit(listOfCastes.size) //you can set maximum checkbox selection limit (Optional)
                        .multiSelectList(listOfCastes) // the multi select model list with ids and name
                        .preSelectIDsList(preselected)
                        .onSubmit(object : MultiSelectDialog.SubmitCallbackListener{
                            override fun onSelected(
                                p0: ArrayList<Int>,
                                p1: ArrayList<String>?,
                                p2: String?
                            ) {
                                id_array_caste = arrayListOf()
                                name_array_caste =  arrayListOf()
                                id_religion_for_caste = arrayListOf()
                                chipGroupCaste.removeAllViews()
                                for (i in 0 until p0.size) {
                                    if (p0[i] != 0) {
                                        id_array_caste.add(p0[i])

                                        casteNameForCaste[p0[i]]?.let { it1 ->
                                            name_array_caste.add(
                                                it1
                                            )
                                        }

                                        religionForCaste[p0[i]]?.let { it1 ->
                                            id_religion_for_caste.add(
                                                it1
                                            )
                                        }
                                        casteNameForCaste[p0[i]]?.let { it1 -> selection(it1, chipGroupCaste) }
                                    }
                                }
                                caste_socialfilter_number_selected?.text = p0.size.toString()+" Selected"
                            }

                            override fun onCancel() {

                            }
                        })
                        .show(getSupportFragmentManager(), "multiSelectDialog")

                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        //observe API call status
        viewModel.CasteInfoApiCallStatus.observe(this, Observer {
            processStatus1(it)
        })

    }



    private fun setupObserversFilterData() {
        viewModelSocialFilter.socialFilterResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    filter_main(it)
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        //observe API call status
        viewModelSocialFilter.socialFilterAPICallStatus.observe(this, Observer {
            processStatus1(it)
        })

    }


    private fun setupObserversSubmit() {
        viewModelSocialFilter.socialFilterSubmitResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    Toast.makeText(this, "Preferences updated successfully", Toast.LENGTH_SHORT).show()
                    val actual_intent = intent
                    if (actual_intent != null && actual_intent.getExtras() != null &&
                        actual_intent.getExtras()!!.containsKey("get_back")){
                        val intent = Intent(this, NeuroMatchedUsers::class.java)
                        startActivity(intent)
                    }else {
                        val intent = Intent(this, HomePage1::class.java)
                        startActivity(intent)
                    }
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        //observe API call status
        viewModelSocialFilter.socialFilterSubmitAPICallStatus.observe(this, Observer {
            processStatus1(it)
        })
    }


    private fun processStatus1(resource: ResourceStatus) {

        when (resource.status) {
            StatusType.SUCCESS -> {
                dismissDialog()


            }
            StatusType.EMPTY_RESPONSE -> {
                dismissDialog()

            }
            StatusType.PROGRESSING -> {
                showDialog()

            }
            StatusType.SWIPE_RELOADING -> {

            }
            StatusType.ERROR -> {
                Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
                dismissDialog()

            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                Toast.makeText(this, "session expired", Toast.LENGTH_SHORT).show()
            }
        }

    }


}
