package pl.lukaszbialobrzeski.monkmodeapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import pl.lukaszbialobrzeski.monkmodeapp.databinding.ActivityMainBinding

// MainActivity class handles the main user interface of the launcher.
class MainActivity : AppCompatActivity() {

    // Binding object instance with access to views in the activity_main.xml layout
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = checkNotNull(_binding) { "Activity has been destroyed" }

    // List of all installed apps and the adapter for the ListView
    private lateinit var allApps: List<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout for this activity using ViewBinding
        _binding = ActivityMainBinding.inflate(layoutInflater)

        // Set the content view to the binding's root view
        setContentView(binding.root)

        // Get the list of installed applications
        allApps = getInstalledApps()

        // Set up the adapter for the ListView to display the apps
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, allApps)
        binding.listView.adapter = adapter

        // Set up the SearchView to filter the list of apps based on user input
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    // Function to get the list of installed apps and return their names as a list of strings
    private fun getInstalledApps(): List<String> {
        val packageManager: PackageManager = packageManager
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        return packages.map { packageManager.getApplicationLabel(it).toString() }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clear the binding when the activity is destroyed
        _binding = null
    }
}
