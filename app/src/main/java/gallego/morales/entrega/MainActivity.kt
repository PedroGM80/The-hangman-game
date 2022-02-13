package gallego.morales.entrega

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import gallego.morales.entrega.GameFragment.Companion.fail
import gallego.morales.entrega.GameFragment.Companion.ok
import gallego.morales.entrega.databinding.ActivityMainBinding
import gallego.morales.entrega.db.AppDatabase
import gallego.morales.entrega.db.Record
import gallego.morales.entrega.ui.home.HomeFragment.Companion.nombre
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

var allPark: MutableList<Record>? = null

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        GlobalScope.launch {
            allPark = AppDatabase.getDatabase(applicationContext).recordDao().getAll()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun showText(view: View) {
        val listRecords = ArrayList<String>()
        val listview = findViewById<ListView>(R.id.simple_list_item_1)
        allPark?.forEach {
            val instanceName = it.toString()
            listRecords.add(instanceName)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listRecords)
        listview.adapter = adapter
        Log.i("Grabado de:::", allPark.toString())

    }

    fun insertar(view: View) {
        var indice = 0
        indice = (allPark?.size)?.plus(1)!!
        var aRecord = Record(indice, nombre, ok , fail  )
        allPark?.add(aRecord)

    }

    fun borrar(view: View) {
        allPark?.clear()
    }

    fun verPuntuaciones(view: View) {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.action_winFragment_to_recordsFragment)
    }

    fun replayWin(view: View) {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.action_winFragment_to_nav_home)
    }

    fun replayLost(view: View) {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.action_loseFragment_to_nav_home)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun replay(view: View) {
        var saveGame: MutableList<Record>?
        saveGame= allPark as MutableList<Record>
        Log.i("Datos:::", saveGame.toString())
        GlobalScope.launch {
            AppDatabase.getDatabase(applicationContext).recordDao().insertAll(saveGame)
        }
    }

}