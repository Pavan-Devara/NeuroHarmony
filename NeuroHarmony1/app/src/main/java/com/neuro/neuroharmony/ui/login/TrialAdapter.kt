package com.neuro.neuroharmony.ui.login

//package com.neuro.neuroharmony.ui.login
//
//import android.view.View
//import android.view.View.inflate
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.neuro.neuroharmony.R
//
//class TrialAdapter(
//    users: ArrayList<FixTestAsDefault.User_CoreBelief>,
//    viewModel1: ScoreNeuroDesireViewModel,
//    test: String,
//    viewModel4: BaseLineViewModel,
//    type: Int
//) : RadioAdapter<ArrayList<FixTestAsDefault.User_CoreBelief>(users) {
//    override fun onBindViewHolder(holder: RadioViewHolder, position: Int) {
//        super.onBindViewHolder(holder, position)
//    }
//}
//
//abstract class RadioAdapter<T>(private val items: List<T>) : RecyclerView.Adapter<RadioAdapter<T>.RadioViewHolder>() {
//
//    private var selectedPosition = -1
//
//    inner class RadioViewHolder(val binding: ViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        private val clickHandler: (View) -> Unit = {
//            selectedPosition = adapterPosition
//            notifyDataSetChanged()
//        }
//
//        init {
//            binding.apply {
//                radioButton.setOnClickListener(clickHandler)
//            }
//        }
//    }
//
//    override fun getItemCount() = items.size
//
//    operator fun get(position: Int): T = items[position]
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//        inflate<ViewItemBinding>(from(parent.context), R.layout.fix_as_defalut_test, parent, false).run {
//            RadioViewHolder(this)
//        }
//
//    override fun onBindViewHolder(holder: RadioViewHolder, position: Int) {
//        holder.binding.radioButton.isChecked = position == selectedPosition
//    }
//
//}