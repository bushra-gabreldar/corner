package example.hellojaleel.corner.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.ParseFile;

import java.util.List;

import example.hellojaleel.corner.R;
import example.hellojaleel.corner.models.Take;

public class takesAdapter extends RecyclerView.Adapter<takesAdapter.ViewHolder>{

    private Context context;
    private List<Take> takes;

    public takesAdapter(Context context, List<Take> takes) {
        this.context = context;
        this.takes = takes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_take, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Take take = takes.get(position);
        holder.bind(take);
    }

    @Override
    public int getItemCount() {
        return takes.size();
    }



    //Clean all elements of the recycler
    public void clear() {
        takes.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Take> list) {
        takes.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //track view objects
        public ImageView ivProfileImage;
        public TextView tvUserName;
        public TextView tvBody;
        public TextView tvTimestamp;


        public ViewHolder(View itemView) {
            super(itemView);
            // lookup view objects by id
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);


        }

        public void bind(Take take) {
            tvUserName.setText(take.getUser().getUsername());
            ParseFile image = take.getUser().getParseFile("profileImage");
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivProfileImage);
            }
        }


    }
}
