package com.all.all

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.system.Os.remove
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding.widget.RxTextView
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class MainActivity : AppCompatActivity() {

    private lateinit var editeText: EditText
    private lateinit var text: TextView
    private lateinit var button: Button
    private lateinit var textResult: TextView

    private lateinit var btnClean: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: adapter
    private var list: ArrayList<String> = ArrayList()
    lateinit var sp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editeText = findViewById(R.id.edittextT)
        text = findViewById(R.id.textT)
        button = findViewById(R.id.btnADD)
        textResult = findViewById(R.id.result)
        btnClean = findViewById(R.id.btnClean)
        recyclerView = findViewById(R.id.recyclerView)
        adapter = adapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)


    }


    override fun onStart() {
        super.onStart()
        button()
        swipe()
        loadShare()
    }

    private fun swipe() {
        val item = object : SwipeToDelete(this, 0, ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                list.removeAt(viewHolder.adapterPosition)
                adapter.setup(list)
                adapter.notifyDataSetChanged()
                resultFun()
            }
        }

        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun сalculations(): String {
        var sum: Long = 0
        for (i in list) {

            try {
                sum = sum + Integer.valueOf(i)
            } catch (ex: NumberFormatException) {
                System.out.println("not a number" + ex);
            }


        }
        return sum.toString()

    }

    fun button() {
        button.setOnClickListener {
            var ll = editeText.text
            list.add(ll.toString())
            Log.d("WWW", "TEST = " + сalculations())

            adapter.setup(list)

            resultFun()
            saveElement()
        }




        btnClean.setOnClickListener {
            list.clear()
            adapter.setup(list)
            adapter.notifyDataSetChanged()
            textResult.text = "0"
            clearShare()

        }


    }

    private fun clearShare() {

        sp = getSharedPreferences("App_settings", Context.MODE_PRIVATE)
        var ed = sp.edit()
        var set: HashSet<String> = HashSet()
        list.clear()
        set.addAll(list)

        ed.putStringSet("Save", set)
        ed.apply()

        Log.d("YYY", "ВСЕ УСПЕШНО = ")

    }

    fun resultFun() {
        var lets = сalculations()
        textResult.text = "$lets"
    }


    override fun onDestroy() {
        super.onDestroy()
        saveElement()


    }

    fun saveElement() {

        sp = getSharedPreferences("App_settings", Context.MODE_PRIVATE)
        var ed = sp.edit()
        var set: HashSet<String> = HashSet()
        set.addAll(list)

        ed.putStringSet("Save", set)
        ed.apply()

        Log.d("YYY", "ВСЕ УСПЕШНО = ")

    }

    fun loadShare() {

        sp = getSharedPreferences("App_settings", Context.MODE_PRIVATE)

        try {
            var set = sp.getStringSet("Save", null)

            if (set == null) {
                Log.d("YYY", "YYYY = " + set)
            } else {
                list.addAll(set)
                adapter.setup(list)
                resultFun()
            }


        } catch (e: Exception) {

        }


    }

}


