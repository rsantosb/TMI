package tmi.busho;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class MostrarRA extends AppCompatActivity {

    // Set to true ensures requestInstall() triggers installation if necessary.
    private boolean mUserRequestedInstall = true;

    Session mSession;
    private ArFragment arFragment;
    ViewRenderable textArViewRenderable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_r);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        // Enable AR related functionality on ARCore supported devices only.
        //maybeEnableArButton();

        ViewRenderable.builder()
                .setView(this, R.layout.texto_ar)
                .build()
                .thenAccept(renderable -> textArViewRenderable = renderable);
    }

    /*void maybeEnableArButton() {
        ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(this);
        if (availability.isTransient()) {
            // Re-query at 5Hz while compatibility is checked in the background.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    maybeEnableArButton();
                }
            }, 200);
        }
        if (availability.isSupported()) {
            //mArButton.setVisibility(View.VISIBLE);
            //mArButton.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Su dispositivo SÍ soporta RA", Toast.LENGTH_SHORT).show();
            // indicator on the button.
        } else { // Unsupported or unknown.
            //mArButton.setVisibility(View.INVISIBLE);
            //mArButton.setEnabled(false);
            Toast.makeText(getApplicationContext(), "Su dispositivo NO soporta RA", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();

        // Check camera permission.
        // ARCore requires camera permission to operate.
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            CameraPermissionHelper.requestCameraPermission(this);
            return;
        }

        // Make sure ARCore is installed and up to date.
        try {
            if (mSession == null) {
                switch (ArCoreApk.getInstance().requestInstall(this, mUserRequestedInstall)) {
                    case INSTALLED:
                        // Success, create the AR session.
                        mSession = new Session(this);
                        break;
                    case INSTALL_REQUESTED:
                        // Ensures next invocation of requestInstall() will either return
                        // INSTALLED or throw an exception.
                        mUserRequestedInstall = false;
                        return;
                }
            }
        } catch (UnavailableUserDeclinedInstallationException | UnavailableDeviceNotCompatibleException | UnavailableArcoreNotInstalledException | UnavailableApkTooOldException | UnavailableSdkTooOldException e) {
            // Display an appropriate message to the user and return gracefully.
            Toast.makeText(this, "TODO: handle exception " + e, Toast.LENGTH_LONG).show();
            return;
        }
        return;  // mSession is still null.
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            Toast.makeText(this, "Camera permission is needed to run this application", Toast.LENGTH_LONG)
                    .show();
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                CameraPermissionHelper.launchPermissionSettings(this);
            }
            finish();
        }
    }

}
