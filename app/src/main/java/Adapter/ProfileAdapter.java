package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import Model.ProfileModel;
import gp.geniusplazaproject.R;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {


    private Context mContext;
    private ArrayList<ProfileModel> mExampleList;

    public ProfileAdapter(Context context, ArrayList<ProfileModel> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.person_profile, parent, false);
        return new ProfileViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        ProfileModel currentProfile = mExampleList.get(position);

        int id = currentProfile.getId();
        String fName = currentProfile.getfName();
        String lName = currentProfile.getlName();
        String imageUrl = currentProfile.getImageUrl();


        holder.mName.setText("Name: " +fName +" " + lName);
        holder.mId.setText("ID: "+ String.valueOf(id));
        Picasso.get().load(imageUrl).error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder).fit().centerInside().into(holder.mImg);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImg;
        public TextView mName;
        public TextView mId;

        public ProfileViewHolder(View itemView) {
            super(itemView);

            mId = itemView.findViewById(R.id.profile_id);
            mName = itemView.findViewById(R.id.profile_name);
            mImg = itemView.findViewById(R.id.profile_image);
        }
    }
}
