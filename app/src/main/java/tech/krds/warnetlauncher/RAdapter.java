package tech.krds.warnetlauncher;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.os.Vibrator;

import static android.content.Context.VIBRATOR_SERVICE;

public class RAdapter extends RecyclerView.Adapter<RAdapter.ViewHolder> {
    private List<AppInfo> appsList;
    private Context mContext;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener {
        public TextView textView;
        public ImageView img;
        private DatabaseHandler databaseHandler;

        //This is the subclass ViewHolder which simply
        //'holds the views' for us to show on each row
        public ViewHolder(View itemView) {
            super(itemView);
            databaseHandler = new DatabaseHandler(itemView.getContext());
            //Finds the views from our row.xml
            textView = (TextView) itemView.findViewById(R.id.appName_tv);
            img = (ImageView) itemView.findViewById(R.id.appImage_iv);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick (View v) {
            int pos = getAdapterPosition();
            Context context = v.getContext();

            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(appsList.get(pos).packageName.toString());
            context.startActivity(launchIntent);
           // Toast.makeText(v.getContext(), appsList.get(pos).label.toString(), Toast.LENGTH_LONG).show();

        }

        @Override
        public boolean onLongClick (View v) {
            if (Build.VERSION.SDK_INT >= 26) {
                ((Vibrator) v.getContext().getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                ((Vibrator) v.getContext().getSystemService(VIBRATOR_SERVICE)).vibrate(150);
            }
            final int pos = getAdapterPosition();
            final Dialog dialog = new Dialog(v.getContext());
            dialog.setContentView(R.layout.dialog_layout);
            dialog.setTitle(appsList.get(pos).label.toString());
            ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
            TextView textView = (TextView) dialog.findViewById(R.id.appName_tv);
            TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);

            imageView.setImageDrawable(appsList.get(pos).icon);
            textView.setText(easterEggHandler(appsList.get(pos).label));

            textView2.setText(appsList.get(pos).packageName);
            Button button = (Button) dialog.findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View view)
                {
                    databaseHandler.addRecord(appsList.get(pos).packageName.toString());
                    listener.initFavorite();
                    dialog.cancel();
                    Toast.makeText(view.getContext(),appsList.get(pos).label + " Telah ditambahkan ke Aplikasi Favorit",Toast.LENGTH_SHORT).show();
                }
            });
            Button button2 = (Button) dialog.findViewById(R.id.button2);

            button2.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View view)
                {
                    dialog.cancel();
                    Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setData(Uri.parse("package:" + appsList.get(pos).packageName));
                    view.getContext().startActivity(i);

                }
            });
            Button button3 = (Button) dialog.findViewById(R.id.button3);

            button3.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View view)
                {
                    dialog.cancel();
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + appsList.get(pos).packageName));
                    view.getContext().startActivity(intent);

                }
            });
            dialog.show();


            // Toast.makeText(v.getContext(), appsList.get(pos).label.toString(), Toast.LENGTH_LONG).show();
            return true;
        }
    }

    String easterEggHandler(CharSequence babi) {
        String label = babi.toString();

        if (label.contains("Free Fire")) {
            label = "Gim Tanpa Pintu";
        } else if (label.contains("Mobile Legends")) {
            label =  "Analog 8 Bit";
        } else if (label.equalsIgnoreCase("Binomo")) {
            label =  "Jutaan orang tidak menyadari";
        } else if (label.contains("PUBG Mobile")) {
            label = "Gim Haram";
        } else if (label.contains("Shopee")) {
            label = "SiMontok";
        } else if (label.contains("BUROQ")) {
            label = "Beraq";
        } else if (label.equalsIgnoreCase("Lite")) {
            label = "FB Misqueen";
        } else if (label.contains("Youtube Go")) {
           label = "Youtube Misqueen";
        }

        return label;

    }

    private IMethodCaller listener;
    public RAdapter(Context c, IMethodCaller listener) {

        //This is where we build our list of app details, using the app
        //object we created to store the label, package name and icon
        this.mContext = c;
        this.listener = listener;
        PackageManager pm = c.getPackageManager();
        appsList = new ArrayList<AppInfo>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
        for(ResolveInfo ri:allApps) {
            AppInfo app = new AppInfo();
            app.label = ri.loadLabel(pm);
            app.packageName = ri.activityInfo.packageName;
            app.icon = ri.activityInfo.loadIcon(pm);
            appsList.add(app);
        }
        if (appsList.size() > 0) {
            Collections.sort(appsList, new Comparator<AppInfo>() {
                @Override
                public int compare(final AppInfo object1, final AppInfo object2) {
                    return object1.getLabel().toString().compareToIgnoreCase(object2.getLabel().toString());

                }
            });
        }

    }

    @Override
    public void onBindViewHolder(RAdapter.ViewHolder viewHolder, int i) {

        //Here we use the information in the list we created to define the views

        String appLabel = appsList.get(i).label.toString();
        String appPackage = appsList.get(i).packageName.toString();
        Drawable appIcon = appsList.get(i).icon;

        TextView textView = viewHolder.textView;
        textView.setText(appLabel);
        ImageView imageView = viewHolder.img;
        imageView.setImageDrawable(appIcon);
    }


    @Override
    public int getItemCount() {

        //This method needs to be overridden so that Androids knows how many items
        //will be making it into the list

        return appsList.size();
    }


    @Override
    public RAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //This is what adds the code we've written in here to our target view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.apps_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
}