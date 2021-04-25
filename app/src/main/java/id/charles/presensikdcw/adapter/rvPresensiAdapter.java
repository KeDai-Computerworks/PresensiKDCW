package id.charles.presensikdcw.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

import id.charles.presensikdcw.R;
import id.charles.presensikdcw.ScannerActivity;
import id.charles.presensikdcw.database.AppDatabase;
import id.charles.presensikdcw.database.Kegiatan;

public class rvPresensiAdapter extends RecyclerView.Adapter<rvPresensiAdapter.KegiatanViewHolder> {

    private Context context;
    private ArrayList<Kegiatan> results;
    private AppDatabase appDatabase;

    public rvPresensiAdapter(Context context, ArrayList<Kegiatan> results) {
        this.context = context;
        this.results = results;

        appDatabase = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class, "dbKegiatan").allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public KegiatanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_presensi, parent, false);
        KegiatanViewHolder holder = new KegiatanViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull rvPresensiAdapter.KegiatanViewHolder holder, int position) {

//        holder.tvCreated.setText(results.get(position).getId());
        holder.tvTitle.setText(results.get(position).getNama());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ScannerActivity.class).
                        putExtra("id", results.get(position).getId()).
                        putExtra("idAbsensi", results.get(position).getIdAbsensi()));
            }
        });

        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                Toast.makeText(context, "id " + results.get(position).getId()+
//                        "\n nama"+results.get(position).getNama()+
//                        "\n catatan"+results.get(position).getCatatan()+
//                        "\n id absensi"+ results.get(position).getIdAbsensi(), Toast.LENGTH_SHORT).show();
//                return true;
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Hapus Rapat Ini ?");
                alertDialogBuilder.setNegativeButton("tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialogBuilder.setPositiveButton(Html.fromHtml("<font color='#FF2727'>YA</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        onDeleteData(position);
                        Toast.makeText(context, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.create();
                alertDialogBuilder.show();
                return true;
            }
        });
    }
    private void onDeleteData(int position) {
        appDatabase.kegiatanDao().deleteKegiatan(results.get(position));
        results.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, results.size());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class KegiatanViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCreated, tvUpdated;
        CardView cv;

        public KegiatanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
//            tvCreated = itemView.findViewById(R.id.tv_created);
//            tvUpdated = itemView.findViewById(R.id.tv_updated);
            cv = itemView.findViewById(R.id.cv_kegiatan);
        }
    }
}

