package com.example.tp3.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tp3.R
import com.example.tp3.data.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var Pseudo: EditText? = null
    private var BtnOK: Button? = null
    private var Mdp: EditText? = null

    val userRepository by lazy { UserRepository.newInstance(application) }

    private val activityScope = CoroutineScope(
        SupervisorJob()
                + Dispatchers.Main
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeVariables()
        BtnOK!!.setOnClickListener(this)

        
        val l=sp.getString("login","null")
        Pseudo?.setText(l.toString())
    }

    fun initializeVariables(){
        sp = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sp.edit()

        //sp = getSharedPreferences("sp",Context.MODE_PRIVATE)
        Pseudo = findViewById(R.id.Pseudo)
        BtnOK = findViewById(R.id.ButtonOk)
        Mdp = findViewById(R.id.editPass)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_settings -> {
                val iGP = Intent(this, SettingsActivity::class.java)
                startActivity(iGP)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id)
        {
            R.id.ButtonOk ->
            {
                login()
            }
        }
    }

    fun login(){
        activityScope.launch {
            try {
                val hash = userRepository.connexion(Pseudo?.text.toString(),  Mdp?.text.toString())
                if(hash.isNotEmpty())
                {
                    //Garder dans shared preferences
                    editor.putString("login", Pseudo?.text.toString())
                    editor.commit()
                    val l=sp.getString("login","null")
                    Pseudo?.setText(l.toString())
                    //Changer Activite
                    val versSecondAct: Intent =
                        Intent(this@MainActivity, ChoixListActivity::class.java)
                    //Envoyer donnes
                    //versSecondAct.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    versSecondAct.putExtra("pseudo", Pseudo?.text.toString())
                    versSecondAct.putExtra("hash", hash)
                    // todo
                    versSecondAct.putExtra("id_user", "1")
                    startActivity(versSecondAct)
                }
                else
                    Toast.makeText(this@MainActivity, "Erreur de Connection", Toast.LENGTH_SHORT).show()
            }
            catch (e:Exception)
            {
                Toast.makeText(this@MainActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}