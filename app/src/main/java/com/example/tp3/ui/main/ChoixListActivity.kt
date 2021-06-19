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
import com.example.tp3.data.ListRepository
import com.example.tp3.data.UserRepository
import com.example.tp3.data.model.Item
import com.example.tp3.ui.main.adapter.AdapterList
import com.example.tp3.ui.main.viewmodel.ListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ChoixListActivity : AppCompatActivity(){
    private var pseudo: String? = null
    private var hash: String? = null
    private var id_user: String? = null
    private var id_user_int: Int? = null
    private var adapter: AdapterList? = null
    private var b: Button? = null
    private var t: EditText? = null
    private var recyclerView: RecyclerView?= null

    private var lists: MutableList<com.example.tp3.data.model.List>? = null
    private var change: Intent? = null

    private val listViewModel by viewModels<ListViewModel>()

    val listRepository by lazy { ListRepository.newInstance(application) }

    private val activityScope = CoroutineScope(
        SupervisorJob()
                + Dispatchers.Main
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)
        initializeVariables()
        setUpRecyclerView()
        loadlistes()
        changeToShowListActivity()
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
        activityScope.launch {
            try {
                if(id_user_int!=null && hash!=null){
                    val lists= listRepository.getListsUser(hash!!)
                    adapter!!.addData(lists)
                }

            }catch (e:Exception){
                Toast.makeText(this@ChoixListActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        b?.setOnClickListener {
            addList()
        }

    }

    fun setUpRecyclerView(){
        lists = mutableListOf()
        recyclerView = findViewById<RecyclerView>(R.id.RecyclerViewChListe)

        adapter = AdapterList(lists!!)

        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        recyclerView?.visibility = View.GONE
        loadlistes()
        recyclerView?.visibility = View.VISIBLE



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

    fun loadlistes() {
        try{
            if(hash!=null){
                Toast.makeText(this@ChoixListActivity, "load lists", Toast.LENGTH_SHORT).show()
                listViewModel.getListsUser(hash!!)
                listViewModel.lists.observe(this) { viewState ->
                    when (viewState) {
                        is ListViewModel.ViewState.Content -> {
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
    }

    fun addList(){
        activityScope.launch {
            try {
                if(id_user_int!=null && hash!=null){
                    recyclerView?.visibility = View.GONE
                    val newListName = t?.text.toString()
                    Toast.makeText(this@ChoixListActivity, newListName, Toast.LENGTH_SHORT).show()


                    listRepository.mkListUser(id_user_int!!, newListName, hash!!)

                    val lists= listRepository.getListsUser(hash!!)
                    adapter!!.addData(lists)


                    t?.setText("")
                    recyclerView?.visibility = View.VISIBLE
                }

            }catch (e:Exception){
                Toast.makeText(this@ChoixListActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showProgress(show: Boolean) {
        val progress = findViewById<View>(R.id.progressBarCh)
        val list = findViewById<View>(R.id.RecyclerViewChListe)
        progress.isVisible = show
        list.isVisible = !show
    }


}
