package ng.com.techdepo.textrecognition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.ActivityNotFoundException
import android.net.Uri




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title="Scan! Copy! Recognize"

        copy.setOnClickListener {
            startActivity(Intent(this,TextRecognition::class.java))
        }

        scan.setOnClickListener {

            startActivity(Intent(this, BarCodeScaner::class.java))
        }

        recognize.setOnClickListener {
            startActivity(Intent(this, ImageRecognition::class.java))
        }

        rate.setOnClickListener {
            rateApp()
        }

        dev.setOnClickListener {

            startActivity(Intent(this,DeveloperProfile::class.java))
        }

    }


 fun rateApp() {

val uri = Uri.parse("market://details?id=" + this.packageName)
val goToMarket = Intent(Intent.ACTION_VIEW, uri)
 // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
try
{
startActivity(goToMarket)
}
catch (e:ActivityNotFoundException) {
startActivity(Intent(Intent.ACTION_VIEW,
Uri.parse("http://play.google.com/store/apps/details?id=" + this.packageName)))
}

}

}
