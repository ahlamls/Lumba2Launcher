package tech.krds.warnetlauncher;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.VIBRATOR_SERVICE;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.DaftarViewHolder> {
    private DatabaseHandler databaseHandler;
    String uid,nohp,session;
    SharedPreferences sharedpreferences;
    private ArrayList<FavoriteModel> dataList;
    private IMethodCaller listener;
    public FavoriteAdapter(ArrayList<FavoriteModel> dataList,IMethodCaller listener) {
        this.listener = listener;
        this.dataList = dataList;
    }

    @Override
    public DaftarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.f_apps_row, parent, false);
        return new DaftarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DaftarViewHolder holder, final int position) {
//        holder.txtNama.setText(dataList.get(position).getNama());
//        holder.txtNpm.setText(dataList.get(position).getNpm() + " MeterPoin");
        holder.appName_tv.setText(dataList.get(position).getLabel());
        holder.appImage_iv.setBackground(dataList.get(position).getIcon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                //int pos = getAdapterPosition();
                Context context = v.getContext();

                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(dataList.get(position).getPackageName().toString());
                context.startActivity(launchIntent);
                //Toast.makeText(v.getContext(), appsList.get(pos).label.toString(), Toast.LENGTH_LONG).show();
            }
        });



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override public boolean onLongClick(View v) {


                if (Build.VERSION.SDK_INT >= 26) {
                    ((Vibrator) v.getContext().getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    ((Vibrator) v.getContext().getSystemService(VIBRATOR_SERVICE)).vibrate(150);
                }
                final int pos = position;
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_layout);
                dialog.setTitle(dataList.get(pos).label.toString());
                ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
                TextView textView = (TextView) dialog.findViewById(R.id.appName_tv);
                TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);

                imageView.setImageDrawable(dataList.get(pos).icon);
                textView.setText(dataList.get(pos).label);
                textView2.setText(dataList.get(pos).packageName);
                Button button = (Button) dialog.findViewById(R.id.button);
                button.setText("Hapus dari Desktop");
                button.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_favorite_border_black_24dp, 0, 0, 0);
                button.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        databaseHandler.deleteModel(Integer.parseInt(dataList.get(pos).getId()));


                        listener.initFavorite();
                        dialog.cancel();
                        Toast.makeText(view.getContext(),dataList.get(pos).label + " Telah dihapus dari Aplikasi Favorit",Toast.LENGTH_SHORT).show();
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
                        i.setData(Uri.parse("package:" + dataList.get(pos).packageName));
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
                        intent.setData(Uri.parse("package:" + dataList.get(pos).packageName));
                        view.getContext().startActivity(intent);

                    }
                });
                dialog.show();


                // Toast.makeText(v.getContext(), appsList.get(pos).label.toString(), Toast.LENGTH_LONG).show();
                return true;

            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DaftarViewHolder extends RecyclerView.ViewHolder{
        private TextView appName_tv;
        private ImageView appImage_iv;

        public DaftarViewHolder(View itemView) {
            super(itemView);
            databaseHandler = new DatabaseHandler(itemView.getContext());
            appImage_iv = (ImageView) itemView.findViewById(R.id.appImage_iv);
            appName_tv = (TextView) itemView.findViewById(R.id.appName_tv);


            // txtNoHp = (TextView) itemView.findViewById(R.id.txt_nohp_Daftar);

        }
    }




}
