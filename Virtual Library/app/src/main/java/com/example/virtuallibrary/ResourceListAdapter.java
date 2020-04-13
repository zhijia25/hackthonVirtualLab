package com.example.virtuallibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.resource.lib.GeneralResource;
import com.resource.lib.PdfResource;

import java.util.ArrayList;
import java.util.List;


public class ResourceListAdapter extends BaseAdapter implements View.OnClickListener {
    List<PdfResource> resourceList = new ArrayList<>();
    private Context mContext;
    private Callback mCallback;

    public ResourceListAdapter(Context context, Callback callback) {
        mContext = context;
        mCallback = callback;
    }

    public void setResourceList(List<PdfResource> resourceList) {
        this.resourceList = resourceList;
    }

    public List<PdfResource> getResourceList() {
        return this.resourceList;
    }

    @Override
    public int getCount() {
        return this.resourceList.size();
    }

    @Override
    public Object getItem(int position) {
        return getCount() == 0 ? null : this.resourceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.resource_snapshot, null);
            viewHolder = new ViewHolder();
            viewHolder.itemName = view.findViewById(R.id.resource_snapshot_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.itemName.setText(this.resourceList.get(i).getTitle());
        viewHolder.itemName.setOnClickListener(this);
        viewHolder.itemName.setTag(i);
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

    class ViewHolder {
        TextView itemName;
    }

    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }

    public interface Callback {
        void click(View v);
    }
}
