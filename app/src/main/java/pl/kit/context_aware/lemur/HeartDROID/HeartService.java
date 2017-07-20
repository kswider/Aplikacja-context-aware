package pl.kit.context_aware.lemur.HeartDROID;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;

/**
 * Created by Tomek on 2017-07-19.
 */

public class HeartService extends IntentService {

    /**
     * Needed constructor
     */
    public HeartService() {
        super("Heart Service");
    }

    /**
     * Runs all models
     * @param intent intent sent to service
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Inference inference = new Inference(this);
        for(String scriptName : FilesOperations.getAllModelNames(this)) {
            inference.runInference(getFilesDir() + "/" + scriptName + ".hmr");
        }
    }
}
