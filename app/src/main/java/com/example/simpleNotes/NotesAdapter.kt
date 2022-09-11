package com.example.simpleNotes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleNotes.database.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*


class NotesAdapter:
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var dataSet: ArrayList<NoteModel> = ArrayList()

    private lateinit var viewModel : NotesViewModel

    private var maximizedLayout: LinearLayout? = null
    private var maximizedPos: Int? = null

    private var context: Context? = null

    private lateinit var rootView : RecyclerView

    fun setRoot(rootView : RecyclerView){
        this.rootView = rootView
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    fun setViewModel(viewModel: NotesViewModel){
        this.viewModel = viewModel
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(notes: ArrayList<NoteModel>){
        dataSet = notes
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val expandLayout : LinearLayout
        //val root : CardView
        val deleteButton : Button
        val dateTextView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.textView)
            expandLayout = view.findViewById(R.id.expandLayout)
            deleteButton = view.findViewById(R.id.deleteButton)
            dateTextView = view.findViewById(R.id.dateTextView)
           // root = view.findViewById(R.id.main)
        }

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = dataSet[position].text

        val simpleDate = SimpleDateFormat("dd/MM/yy hh:mm:ss", Locale.getDefault())
        val date = simpleDate.format(Date(dataSet[position].date!! * 1000))

        viewHolder.dateTextView.text = "Edited \n$date"

        viewHolder.textView.setOnClickListener {
            val i = Intent(viewHolder.textView.context, EditActivity::class.java)
            EditActivity.oldNote = dataSet[position]
            viewHolder.textView.context.startActivity(i)
        }

        viewHolder.deleteButton.setOnClickListener {
            viewModel.deleteNote(dataSet[position].note)
            notifyItemChanged(position)
        }

        // Make sure that view is minimized when entering activity
        viewHolder.expandLayout.visibility = View.GONE
        dataSet[position].isExpanded = false

        viewHolder.textView.setOnLongClickListener {
            val autoTransition = AutoTransition()
            autoTransition.duration = 200
            TransitionManager.beginDelayedTransition(rootView, autoTransition)

            if (!dataSet[position].isExpanded) {
                if (maximizedLayout != null){
                    dataSet[maximizedPos!!].isExpanded = false
                    maximizedLayout!!.visibility = View.GONE
                }

                maximizedPos = position
                maximizedLayout = viewHolder.expandLayout
                viewHolder.expandLayout.visibility = View.VISIBLE
                dataSet[position].isExpanded = true
            } else {
                viewHolder.expandLayout.visibility = View.GONE
                dataSet[position].isExpanded = false
            }

            true
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size


}