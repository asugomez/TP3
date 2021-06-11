package com.example.tp1.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1.R
import com.example.tp1.data.DataProvider
import com.example.tp1.data.model.Item
import com.example.tp1.ui.main.adapter.AdapterItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.http.Header
import retrofit2.http.Path


class ShowListActivity : AppCompatActivity(){
    private val activityScope = CoroutineScope(
        SupervisorJob()
                + Dispatchers.Main
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)
        val list_name: String? = intent.getStringExtra("list")
        this.title = "Items de $list_name"
        val id_list: String? = intent.getStringExtra("id_list")
        val id_list_int: Int? = id_list?.toInt()
        val hash: String? = intent.getStringExtra("hash")

        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerViewChObject)
        val items: MutableList<Item> = mutableListOf()


        val adapter = AdapterItem(items)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)


        //loadItems()
        activityScope.launch {
            recyclerView.visibility = View.GONE
            Toast.makeText(this@ShowListActivity, "load items", Toast.LENGTH_SHORT).show()
            try{
                if(id_list_int!=null && hash!=null){
                    val items = DataProvider.getItemsOfAList(id_list_int, hash)
                    adapter.addData(items)
                }
            }catch(e: Exception){
                Toast.makeText(this@ShowListActivity, "${e.message} ", Toast.LENGTH_SHORT).show()
            }
            recyclerView.visibility = View.VISIBLE
        }

        val b=findViewById<Button>(R.id.buttonOkChObject)
        val t=findViewById<EditText>(R.id.editTextObject)

        b.setOnClickListener {
            // add new item
            activityScope.launch {
                try{
                    if(id_list_int!=null && hash!=null){
                        recyclerView.visibility = View.GONE
                        val labelItem = t.text.toString()
                        Toast.makeText(this@ShowListActivity,labelItem, Toast.LENGTH_SHORT).show()
                        // add the new list
                        val newItem = DataProvider.createItem(id_list_int, labelItem, hash)
                        val listReady : List<Item> = listOf(newItem)
                        adapter.addData(listReady)
                        //val lists = DataProvider.getListsFromApi(hash)
                        //adapter.addData(lists)
                        t.setText("")
                        recyclerView.visibility = View.VISIBLE
                    }
                }catch(e: Exception){
                    Toast.makeText(this@ShowListActivity, "${e.message} ", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}

