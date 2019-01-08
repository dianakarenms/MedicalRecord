package com.medicalrecord.adapters

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicalrecord.calcprenatal.R
import com.medicalrecord.data.RefValue
import com.medicalrecord.data.ViewType
import com.medicalrecord.utils.Constants
import com.medicalrecord.utils.decimalsFormat
import kotlinx.android.synthetic.main.item_ref_value.view.*


class ExpandableValuesAdapter(private val listener: (RefValue, Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>(), SectionHeaderViewHolder.HeaderViewHolderCallback {

    private val VALUE_TYPE = 1
    private val HEADER_TYPE = 2

    private var valuesList: List<RefValue>? = null
    private var valueTypeList: List<String>? = null

    private lateinit var viewTypes: SparseArray<ViewType>
    private lateinit var headerExpandTracker: SparseIntArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View
        return when(viewType) {
            VALUE_TYPE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_ref_value, parent, false)
                ValueViewHolder(view)
            }
            HEADER_TYPE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_ref_value_header, parent, false)
                SectionHeaderViewHolder(view, this)

            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_ref_value, parent, false)
                ValueViewHolder(view)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        val viewType = viewTypes.get(position)
        if (itemViewType == VALUE_TYPE) {
            bindValueViewHolder(holder, viewType)
        } else {
            bindHeaderViewHolder(holder, position, viewType)
        }
    }

    private fun bindHeaderViewHolder(holder: RecyclerView.ViewHolder, position: Int, viewType: ViewType) {
        val dataIndex = viewType.dataIndex
        val headerViewHolder = holder as SectionHeaderViewHolder
        if (valueTypeList != null) {
            headerViewHolder.sectionTitle.text = valueTypeList!![dataIndex]
        }
        if (isExpanded(position)) {
            headerViewHolder.sectionButton.text = "Colapsar"
        } else {
            headerViewHolder.sectionButton.text = "Expandir"
        }
    }

    private fun bindValueViewHolder(holder: RecyclerView.ViewHolder, viewType: ViewType) {
        val dataIndex = viewType.dataIndex
        (holder as ValueViewHolder).bind(valuesList!![dataIndex], dataIndex, listener)
    }

    class ValueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RefValue, position: Int, listener: (RefValue, Int) -> Unit) = with(itemView) {
            val name = Constants.displayNames[item.name]
            itemRefValueNameTxt.text = name
            itemRefValueValueTxt.text = item.value.decimalsFormat(2)
            //setOnClickListener { listener(item, position) }
        }
    }

    override fun getItemCount(): Int {
        var count = 0
        if (valueTypeList != null && valuesList != null) {
            viewTypes.clear()
            var collapsedCount = 0
            for (i in 0 until valueTypeList!!.size) {
                viewTypes.put(count, ViewType(i, HEADER_TYPE))
                count += 1
                val valueType = valueTypeList!![i]
                val childCount = getChildCount(valueType)
                if (headerExpandTracker.get(i) !== 0) {
                    // Expanded State
                    for (j in 0 until childCount) {
                        viewTypes.put(count, ViewType(count - (i + 1) + collapsedCount, VALUE_TYPE))
                        count += 1
                    }
                } else {
                    // Collapsed
                    collapsedCount += childCount
                }
            }
        }
        return count
    }

    override fun getItemViewType(position: Int): Int {
        return if (viewTypes.get(position).type === HEADER_TYPE) {
            HEADER_TYPE
        } else {
            VALUE_TYPE
        }
    }

    private fun getChildCount(type: String): Int {
        return when (type) {
            Constants.SOLUTION -> 21
            Constants.ADDITIONAL_INFO -> 13
            Constants.DOCTOR_REFERENCE -> 17
            else -> 0
        }
    }


    fun setValueListAndType(valuesList: List<RefValue>?, valueTypeList: List<String>?) {
        if (valuesList != null && valueTypeList != null) {
            this.valuesList = valuesList
            this.valueTypeList = valueTypeList
            viewTypes = SparseArray(valuesList.size + valueTypeList.size)
            headerExpandTracker = SparseIntArray(valueTypeList.size)
            // Expand all sections on start
            for (index in 0 .. valueTypeList.size) {
                headerExpandTracker.put(index, 1)
            }
            notifyDataSetChanged()
        }
    }

    override fun onHeaderClick(position: Int) {
        val viewType = viewTypes.get(position)
        val dataIndex = viewType.dataIndex
        val valueType = valueTypeList?.get(dataIndex)
        val childCount = getChildCount(valueType!!)
        if (headerExpandTracker.get(dataIndex) === 0) {
            // Collapsed. Now expand it
            headerExpandTracker.put(dataIndex, 1)
            notifyItemRangeInserted(position + 1, childCount)
        } else {
            // Expanded. Now collapse it
            headerExpandTracker.put(dataIndex, 0)
            notifyItemRangeRemoved(position + 1, childCount)
        }
    }

    override fun isExpanded(position: Int): Boolean {
        val dataIndex = viewTypes.get(position).dataIndex
        return headerExpandTracker.get(dataIndex) === 1
    }
}



