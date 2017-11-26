package List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lyz.uniquefilm.R;

import java.util.List;
import java.util.Objects;

/**
 * Created by lyz on 17-11-19.
 */

public class ViewHolderAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private String mData[];

    public ViewHolderAdapter(Context context,String data[]){
        this.mData=data;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.list_item_movie,null);
            holder=new ViewHolder();
            holder.ivpic=(ImageView)convertView.findViewById(R.id.iv_movie);
            holder.tvmess=(TextView)convertView.findViewById(R.id.tv_movie);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.ivpic.setImageResource(R.mipmap.filmicon);
        holder.tvmess.setText(mData[position]);
        return convertView;
    }

    public final class ViewHolder{
        public ImageView ivpic;
        public TextView tvmess;
    }
}
