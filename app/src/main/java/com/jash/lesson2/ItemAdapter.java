package com.jash.lesson2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jash
 * Date: 15-12-29
 * Time: 上午10:25
 */
public class ItemAdapter extends BaseAdapter {
    private static final String TAG = ItemAdapter.class.getSimpleName();
    private Context context;
    private List<Response.ItemsEntity> list;

    public ItemAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        Response.ItemsEntity item = list.get(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (item.getUser() != null) {
            holder.name.setText(item.getUser().getLogin());

            Picasso.with(context).load(getIconURL(item.getUser().getId(), item.getUser().getIcon()))
                    .transform(new CircleTransformation())
                    .into(holder.icon);
        } else {
            holder.name.setText("匿名用户");
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        holder.content.setText(item.getContent());
        if (item.getImage() == null) {
            holder.image.setVisibility(View.GONE);
        } else {
            holder.image.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(getImageURL(item.getImage()))
                    .resize(parent.getWidth(), 0)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.image);
        }
        return convertView;
    }
    public static String getImageURL(String image){
        String url = "http://pic.qiushibaike.com/system/pictures/%s/%s/%s/%s";
        Pattern pattern = Pattern.compile("(\\d+)\\d{4}");
        Matcher matcher = pattern.matcher(image);
        matcher.find();
        Log.d(TAG, "getImageURL: " + matcher.group());
        return String.format(url, matcher.group(1), matcher.group(), "medium", image);
    }
    public static String getIconURL(long id, String icon){
        String url = "http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s";
        return String.format(url, id / 10000, id, icon);

    }
    public void addAll(Collection<? extends Response.ItemsEntity> collection){
        list.addAll(collection);
        notifyDataSetChanged();
    }
    private static class ViewHolder{
        private ImageView icon;
        private ImageView image;
        private TextView content;
        private TextView name;

        public ViewHolder(View itemView) {
            icon = ((ImageView) itemView.findViewById(R.id.user_icon));
            image = ((ImageView) itemView.findViewById(R.id.image));
            content = ((TextView) itemView.findViewById(R.id.content));
            name = ((TextView) itemView.findViewById(R.id.user_name));
        }
    }
}
