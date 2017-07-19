package pl.kit.context_aware.lemur.HeartDROID;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;

/**
 * Created by Iza on 2017-07-19.
 */

public class HeartService extends IntentService {

    public HeartService() {
        super("Heart Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Inference inference = new Inference(this);
        for(String scriptName : FilesOperations.getAllModelNames(this)) {
            inference.runInference(getFilesDir() + "/" + scriptName + ".hmr");
        }
    }
}
