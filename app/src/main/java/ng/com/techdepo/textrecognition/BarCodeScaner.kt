package ng.com.techdepo.textrecognition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_bar_code_scaner.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class BarCodeScaner : AppCompatActivity(), ZXingScannerView.ResultHandler
{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_code_scaner)

        supportActionBar?.title = "Scanner"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation= 0.0F
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        refresh.setOnClickListener {
            z_xing_scanner.resumeCameraPreview(this)

        }
    }


    override fun handleResult(rawResult: Result?) {
        result_edit.setText(rawResult?.text.toString())
       // z_xing_scanner.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        z_xing_scanner.setResultHandler(this)
        z_xing_scanner.startCamera()
    }

    override fun onPause() {
        super.onPause()

        z_xing_scanner.stopCamera()
    }
}
