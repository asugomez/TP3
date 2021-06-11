package com.example.tp1.ui.main

import android.content.Intent
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
import com.example.tp1.data.DataProvider.createList
import com.example.tp1.ui.main.adapter.AdapterList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ChoixListActivity : AppCompatActivity(){


    private val activityScope = CoroutineScope(
        SupervisorJob()
                + Dispatchers.Main
    )

override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_choix_list)
    val pseudo: String? = intent.getStringExtra("pseudo")
    this.title="Listes de $pseudo"
    //token
    val hash: String? = intent.getStringExtra("hash")
    val id_user: String? = intent.getStringExtra("id_user")
    val id_user_int = id_user?.toInt()


    val lists: MutableList<com.example.tp1.data.model.List> = mutableListOf()
    val recyclerView = findViewById<RecyclerView>(R.id.RecyclerViewChListe)

    val adapter = AdapterList(lists)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    //loadLists()
    activityScope.launch {
        recyclerView.visibility = View.GONE
        try{
            if(hash!=null){
                Toast.makeText(this@ChoixListActivity, "load lists", Toast.LENGTH_SHORT).show()
                val listes = DataProvider.getListsFromApi(hash)
                adapter.addData(listes)
            }
        }catch(e: Exception){
            Toast.makeText(this@ChoixListActivity, "${e.message} ", Toast.LENGTH_SHORT).show()
        }
        recyclerView.visibility = View.VISIBLE
    }

    val b=findViewById<Button>(R.id.buttonOkChListe)
    val t=findViewById<EditText>(R.id.editTextListe)

    b.setOnClickListener {
        // to change --> with the user
        activityScope.launch {
            try{
                Toast.makeText(this@ChoixListActivity, "For id user: $id_user", Toast.LENGTH_SHORT).show()
                if(id_user_int!=null && hash!=null){
                    recyclerView.visibility = View.GONE
                    val newListName = t.text.toString()
                    Toast.makeText(this@ChoixListActivity, newListName, Toast.LENGTH_SHORT).show()
                    // add the new list
                    val new_list = createList(id_user_int, newListName, hash)
                    val listReady : List<com.example.tp1.data.model.List> = listOf(new_list)
                    adapter.addData(listReady)
                    //val lists = DataProvider.getListsFromApi(hash)
                    //adapter.addData(lists)
                    t.setText("")
                    recyclerView.visibility = View.VISIBLE
                }
            }catch (e: Exception){
                Toast.makeText(this@ChoixListActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }


    val change = Intent(this, ShowListActivity::class.java)
    adapter.setOnItemClickListener(object : AdapterList.OnItemClickListener {
        override fun onItemClick(position: Int) {
                val listName = lists[position].label.toString()
                val id_list = lists[position].id.toString()

                //
                change.putExtra("list", listName)
                change.putExtra("id_list", id_list)
                change.putExtra("hash", hash)
                //Toast.makeText(applicationContext, id_list, Toast.LENGTH_SHORT).show()
                startActivity(change)

        }


    })



    }

}
