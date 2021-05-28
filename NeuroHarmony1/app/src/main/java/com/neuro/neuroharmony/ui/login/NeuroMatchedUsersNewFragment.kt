package com.neuro.neuroharmony.ui.login


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.NewMatchedUsersData.ItemMatchedUsers
import com.neuro.neuroharmony.utils.CommonUtils
import com.paginate.Paginate
import com.paginate.recycler.LoadingListItemCreator
import kotlin.contracts.contract


/**
 * A simple [Fragment] subclass.
 */
class NeuroMatchedUsersNewFragment(val group: Int, val jsonArray: String?) : Fragment(), Paginate.Callbacks{

    private lateinit var viewModelUsers: MatchedUsersNewViewModel
    private lateinit var viewModelExpressInterest: ExpressInterestViewModel
    private lateinit var viewModelRevokeInterest: RevokeInterestRequestViewModel

    var dialog2: Dialog? = null

    var gson = Gson()
    var num  =0

    @SuppressLint("WrongConstant")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_neuro_matched_users, container, false)

        val bundle = this.arguments
        //if (bundle != null) {
        //    val group = bundle.getInt("position")
        //    val jsonArray = bundle.getString("jsonArray")

        viewModelUsers = ViewModelProviders.of(this)[MatchedUsersNewViewModel::class.java]
        viewModelExpressInterest = ViewModelProviders.of(this)[ExpressInterestViewModel::class.java]
        viewModelRevokeInterest = ViewModelProviders.of(this)[RevokeInterestRequestViewModel::class.java]

        var recyclerView = view?.findViewById(R.id.matched_users_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        recyclerView.setHasFixedSize(true)


        var nothing_here = view?.findViewById(R.id.nothinghere) as TextView

        val adapter = activity?.let { NeuroMatchedNewRecyclerAdapter(it,
            viewModelExpressInterest, viewModelRevokeInterest, viewLifecycleOwner)
        }

        val users = ArrayList<NeuroMatchedUsers.Userdetails>()

        viewModelUsers.itemPagedList.observe(this, Observer<PagedList<ItemMatchedUsers?>?> {
            adapter?.submitList(it)
        })

        recyclerView.adapter = adapter

        Paginate.with(recyclerView, this)
            .setLoadingTriggerThreshold(2)
            .addLoadingListItem(true)
            .setLoadingListItemCreator(CustomLoadingListItemCreator(activity))
            .build();

        val visibility = hasLoadedAllItems()
        if (visibility){
            recyclerView.visibility = View.GONE
            nothing_here.visibility = View.VISIBLE
        }else{
            recyclerView.visibility = View.VISIBLE
            nothing_here.visibility = View.GONE
        }

        return view

    }

    @Suppress("DataClassPrivateConstructor")
    data class NetworkState private constructor(
        val status: Status,
        val msg: String? = null
    ) {

        enum class Status {
            RUNNING,
            SUCCESS_LOADED, // New
            SUCCESS_EMPTY, // New
            FAILED
        }

        companion object {

            val EMPTY = NetworkState(Status.SUCCESS_EMPTY) // New
            val LOADED = NetworkState(Status.SUCCESS_LOADED) // New
            val LOADING = NetworkState(Status.RUNNING)
            fun error(msg: String?) = NetworkState(Status.FAILED, msg)
        }
    }

    override fun onLoadMore() {
        Toast.makeText(activity, "Loading more", Toast.LENGTH_SHORT).show()
    }

    override fun isLoading(): Boolean {
//        if (dialog2!=null) {
//            dialog2!!.dismiss()
//        }
//        activity?.runOnUiThread {
//            dialog2 = CommonUtils().showDialog(activity!!)
//        }
//        Toast.makeText(activity, "Items are loading" + num.toString(), Toast.LENGTH_SHORT).show()
        return true
    }

    override fun hasLoadedAllItems(): Boolean {
        val count = add(num)
//        Toast.makeText(activity, "Loaded all items" + num.toString(), Toast.LENGTH_SHORT).show()
//        if (dialog2!=null){
//            dialog2!!.dismiss()
//        }

        return num>1
    }

    fun add(number: Int): Int {
        num = number+1
        return num
    }


    private class CustomLoadingListItemCreator(val activity: FragmentActivity?) : LoadingListItemCreator {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view: View =
                inflater.inflate(R.layout.custom_loading_list_item, parent, false)
            return VH(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        }
    }

    internal class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}
