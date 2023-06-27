package com.example.notes_app.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_kotlin.Data_Base.Data_Base_Holder
import com.example.notes_app.AddNote
import com.example.notes_app.Model.Notes_Data
import com.example.notes_app.R
import com.example.notes_app.databinding.ListItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class Notes_Adapter(var activity: Activity, var arrayList: ArrayList<Notes_Data>) :
    RecyclerView.Adapter<Notes_Adapter.Holder>() {
    class Holder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val root = ListItemBinding.inflate(activity.layoutInflater, parent, false)
        return Holder(root)
    }

    private lateinit var btn_pdf_convert: ImageView

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dataBase = Data_Base_Holder(activity)
        //.............................................................>>>>>>
        holder.binding.tvTitle.text = arrayList.get(position).title.toString()
        holder.binding.tvNote.text = arrayList.get(position).note.toString()
        holder.binding.tvDate.text = arrayList.get(position).date.toString()
        //........................................................................... >>>>>

        holder.binding.root.setOnClickListener {
            var id = dataBase.get_id(arrayList.get(position).title)

            val bottomSheetDialog =
                BottomSheetDialog(activity, R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater.from(activity).inflate(
                R.layout.buttom_sheet_layout,
                activity.findViewById<LinearLayout>(R.id.layout_dialog)
            )
            bottomSheetView.findViewById<Button>(R.id.btn_edite).setOnClickListener {
                Toast.makeText(activity, "Edite", Toast.LENGTH_SHORT).show()
                val i = Intent(activity, AddNote::class.java)
                i.putExtra("title", arrayList.get(position).title)
                i.putExtra("note", arrayList.get(position).note)
                i.putExtra("isUpdated", true)
                activity.startActivity(i)
                notifyDataSetChanged()
            }
            bottomSheetView.findViewById<Button>(R.id.btn_delete).setOnClickListener {
                dataBase.delete_notes(id)
                arrayList.removeAt(position)
                notifyDataSetChanged()
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()


            holder.binding.share1.setOnClickListener {
                var value = arrayList.get(position).note.toString()
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, value)
                val chooser = Intent.createChooser(intent, "Share Note")
                activity.startActivity(chooser)
            }


            holder.binding.share2.setOnClickListener {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        activity.requestPermissions(
                            permission,
                            VIEW_MODEL_STORE_OWNER_KEY.hashCode()
                        )
                    } else {
                        val mDoc = com.itextpdf.text.Document()
                        val mFileName = SimpleDateFormat("yyyMMdd_HHmmss", Locale.getDefault())
                            .format(System.currentTimeMillis())
                        val mFilePath =
                            Environment.getExternalStorageDirectory()
                                .toString() + "/" + mFileName + ".pdf"
                        try {
                            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
                            mDoc.open()
                            val data = arrayList.get(position).note.toString().trim()
                            mDoc.addAuthor("KB CODER")
                            mDoc.add(Paragraph(data))
                            mDoc.close()
                            Toast.makeText(
                                activity,
                                "$mFileName.pdf\n is create to \n $mFilePath",
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(activity, "" + e.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                }
            }

            //.......................... كود قديم ......................
//            val dialog = AlertDialog.Builder(activity)
//            dialog.setTitle("Add Product")
//            dialog.setIcon(R.drawable.ic_baseline_add_24)
//            dialog.setPositiveButton("Add") { a, f ->
//
//
//            }
//            dialog.setNeutralButton("Add cart") { a, c ->
//
//            }
//            dialog.create().show()
        }
    }


    override fun getItemCount(): Int {
        return arrayList.size
    }
}