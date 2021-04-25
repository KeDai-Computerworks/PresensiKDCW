package id.charles.presensikdcw.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.charles.presensikdcw.R;
import id.charles.presensikdcw.ui.ScannerActivity;
import id.charles.presensikdcw.model.ModelPresensi;

public class rvPresensiAdapter extends RecyclerView.Adapter<rvPresensiAdapter.agendaViewHolder> {

    private Context context;
    private final List<ModelPresensi> presensis;

    public rvPresensiAdapter(Context context, List<ModelPresensi> presensis) {
        this.context = context;
        this.presensis = presensis;
    }

    @NonNull
    @Override
    public agendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_presensi, parent, false);

        return new agendaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull agendaViewHolder holder, int position) {

        holder.tvTitle.setText(presensis.get(position).getAgenda().getNama());
        holder.tvDate.setText(presensis.get(position).getTglPresensi());
        holder.cvAgendaPresensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ScannerActivity.class)
                        .putExtra("idPresensi"
                                , String.valueOf(presensis.get(position).getIdPresensi())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return presensis.size();
    }

    public static class agendaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate;
        CardView cvAgendaPresensi;

        public agendaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_created);
            cvAgendaPresensi = itemView.findViewById(R.id.cv_presensi);
        }
    }
}