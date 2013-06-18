package org.kde.connect.PackageReceivers;

import android.content.Context;
import android.widget.Toast;

import org.kde.connect.Types.NetworkPackage;

public class PingPackageReceiver implements BasePackageReceiver {

    Context context;

    public PingPackageReceiver(Context ctx) {
        context = ctx;
    }

    @Override
    public void receivePackage(NetworkPackage np) {

        Toast.makeText(context, "Ping!", Toast.LENGTH_LONG).show();
    }
}