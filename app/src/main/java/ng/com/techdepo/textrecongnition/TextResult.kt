package ng.com.techdepo.textrecongnition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_text_result.*

class TextResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_result)

        processed_text.setText(intent.getStringExtra("process_text"))
    }
}
