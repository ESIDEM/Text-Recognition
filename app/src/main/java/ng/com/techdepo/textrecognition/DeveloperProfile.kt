package ng.com.techdepo.textrecognition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DeveloperProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)
        supportActionBar!!.title = "Developer's Profile"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation= 0.0F
    }
}
