package com.example.bai2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MusicBoundService extends Service {
    public MusicBoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}