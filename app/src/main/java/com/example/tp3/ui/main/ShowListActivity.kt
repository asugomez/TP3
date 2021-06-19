package com.example.tp3.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3.R
import com.example.tp3.data.ItemRepository
import com.example.tp3.data.ListRepository
import com.example.tp3.data.model.Item
import com.example.tp3.ui.main.adapter.AdapterItem
import com.example.tp3.ui.main.viewmodel.ItemViewModel
import com.example.tp3.ui.main.viewmodel.ListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class ShowListActivity : AppCompatActivity(){
    private var list_name: String? = null
    private var id_list: String? = null
    private var id_list_int: Int? = null
    private var hash: String? = null
    private var b: Button? = null
    private var t: EditText? = null

    private val activityScope = CoroutineScope(
        SupervisorJob()
                + Dispatchers.Main
    )
    private val itemViewModel by viewModels<ItemViewModel>()

    val itemRepository by lazy { ItemRepository.newInstance(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)
        initializeVariables()
        setUpRecyclerView()
        loadItems()
    }



    fun initializeVariables(){
        list_name = intent.getStringExtra("list")
        id_list = intent.getStringExtra("id_list")
        id_list_int = id_list?.toInt()
        hash= intent.getStringExtra("hash")
        b = findViewById<Button>(R.id.buttonOkChObject)
        t = findViewById<EditText>(R.id.editTextObject)
        this.title = "Items de $list_name"
    }

    fun setUpRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerViewChObject)
        val items: MutableList<Item> = mutableListOf()


        val adapter = AdapterItem(items)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)

        //loadItems()
            recyclerView.visibility = View.GONE
            loadItems()
            recyclerView.visibility = View.VISIBLE


        b?.setOnClickListener {
            // add new item
            activityScope.launch {
                try{
                    if(id_list_int!=null && hash!=null){
                        recyclerView.visibility = View.GONE
                        val labelItem = t?.text.toString()
                        Toast.makeText(this@ShowListActivity,labelItem, Toast.LENGTH_SHORT).show()
                        // add the new list



                        itemRepository.mkItem(id_list_int!!, labelItem,null, hash!!)

                        val lists= itemRepository.getItemsOfAList(id_list_int!!,hash!!)
                        adapter.addData(lists)


                        t?.setText("")
                        recyclerView.visibility = View.VISIBLE
                    }

                }catch (e:Exception){
                    Toast.makeText(this@ShowListActivity, "${e.message} ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadItems() {
        Toast.makeText(this@ShowListActivity, "load items", Toast.LENGTH_SHORT).show()
        try{
            if(id_list_int!=null && hash!=null){
                itemViewModel.items.observe(this) { viewState ->
                    when (viewState) {
                        is ItemViewModel.ViewState.Content -> {
                            showProgress(false)
                        }
                        ItemViewModel.ViewState.Loading -> showProgress(true)
                        is ItemViewModel.ViewState.Error -> {
                            showProgress(false)
                            Toast.makeText(this@ShowListActivity, "${viewState.message} ", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                }
            }
        }catch(e: Exception){
            Toast.makeText(this@ShowListActivity, "${e.message} ", Toast.LENGTH_SHORT).show()
        }

    }
    private fun showProgress(show: Boolean) {
        val progress = findViewById<View>(R.id.progressBarShow)
        val list = findViewById<View>(R.id.RecyclerViewChObject)
        progress.isVisible = show
        list.isVisible = !show
    }
}

