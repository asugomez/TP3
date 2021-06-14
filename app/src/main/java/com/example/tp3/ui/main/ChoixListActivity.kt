package com.example.tp3.ui.main

import android.content.Intent
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
import com.example.tp3.ui.main.adapter.AdapterList
import com.example.tp3.ui.main.viewmodel.ListViewModel

class ChoixListActivity : AppCompatActivity(){
    private var pseudo: String? = null
    private var hash: String? = null
    private var id_user: String? = null
    private var id_user_int: Int? = null
    private var adapter: AdapterList? = null
    private var b: Button? = null
    private var t: EditText? = null

    private var lists: MutableList<com.example.tp3.data.model.List>? = null
    private var change: Intent? = null

    private val listViewModel by viewModels<ListViewModel>()
    /*private val activityScope = CoroutineScope(
        SupervisorJob()
                + Dispatchers.Main
    )*/

    override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_choix_list)
    initializeVariables()
    setUpRecyclerView()


    }

    fun initializeVariables(){
        pseudo = intent.getStringExtra("pseudo")
        //token
        hash = intent.getStringExtra("hash")
        id_user = intent.getStringExtra("id_user")
        id_user_int = id_user?.toInt()
        this.title="Listes de $pseudo"

        b = findViewById<Button>(R.id.buttonOkChListe)
        t = findViewById<EditText>(R.id.editTextListe)

    }

    fun setUpRecyclerView(){
        lists = mutableListOf()
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerViewChListe)

        adapter = AdapterList(lists!!)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        //loadLists()
            recyclerView.visibility = View.GONE
            try{
                if(hash!=null){
                    Toast.makeText(this@ChoixListActivity, "load lists", Toast.LENGTH_SHORT).show()
                    val listes=listViewModel.getListsUser(hash!!)
                    adapter!!.addData(listes)

                    listViewModel.lists.observe(this) { viewState ->
                        when (viewState) {
                            is ListViewModel.ViewState.Content -> {
                                adapter!!.show(viewState.lists)
                                showProgress(false)
                            }
                            ListViewModel.ViewState.Loading -> showProgress(true)
                            is ListViewModel.ViewState.Error -> {
                                showProgress(false)
                                Toast.makeText(this@ChoixListActivity, "${viewState.message} ", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                    }

                }
            }catch(e: Exception){
                Toast.makeText(this@ChoixListActivity, "${e.message} ", Toast.LENGTH_SHORT).show()
            }
            recyclerView.visibility = View.VISIBLE

        b?.setOnClickListener {
            // to change --> with the user
                try{
                    Toast.makeText(this@ChoixListActivity, "For id user: $id_user", Toast.LENGTH_SHORT).show()
                    if(id_user_int!=null && hash!=null){
                        recyclerView.visibility = View.GONE
                        val newListName = t?.text.toString()
                        Toast.makeText(this@ChoixListActivity, newListName, Toast.LENGTH_SHORT).show()
                        // add the new list
                        val new_list = listViewModel.mkListUser(id_user_int!!, newListName, hash!!)

                        val listReady : List<com.example.tp3.data.model.List> = listOf(new_list)
                        adapter!!.addData(listReady)
                        //val lists = DataProvider.getListsFromApi(hash)
                        //adapter.addData(lists)
                        t?.setText("")
                        recyclerView.visibility = View.VISIBLE

                        listViewModel.lists.observe(this) { viewState ->
                            when (viewState) {
                                is ListViewModel.ViewState.Content -> {
                                    adapter!!.show(viewState.lists)
                                    showProgress(false)
                                }
                                ListViewModel.ViewState.Loading -> showProgress(true)
                                is ListViewModel.ViewState.Error -> {
                                    showProgress(false)
                                    Toast.makeText(this@ChoixListActivity, "${viewState.message} ", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }

                        }
                    }
                }catch (e: Exception){
                    Toast.makeText(this@ChoixListActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                }

        }

    }

    fun changeToShowListActivity(){
        change = Intent(this, ShowListActivity::class.java)
        adapter?.setOnItemClickListener(object : AdapterList.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val listName = lists?.get(position)?.label.toString()
                val id_list = lists?.get(position)?.id.toString()

                change!!.putExtra("list", listName)
                change!!.putExtra("id_list", id_list)
                change!!.putExtra("hash", hash)
                //Toast.makeText(applicationContext, id_list, Toast.LENGTH_SHORT).show()
                startActivity(change)
            }
        })

    }

    private fun showProgress(show: Boolean) {
        val progress = findViewById<View>(R.id.progressBarCh)
        val list = findViewById<View>(R.id.RecyclerViewChListe)
        progress.isVisible = show
        list.isVisible = !show
    }


}
