package com.ljr.chat_ljr.frags.panel;

import android.view.View;


import com.ljr.chat_ljr.R;
import com.ljr.common.face.Face;
import com.ljr.common.recycler.RecyclerAdapter;

import java.util.List;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class FaceAdapter extends RecyclerAdapter<Face.Bean> {

    public FaceAdapter(List<Face.Bean> beans, AdapterListener<Face.Bean> listener) {
        super(beans, listener);
    }

    @Override
    protected int getItemViewType(int position, Face.Bean bean) {
        return R.layout.cell_face;
    }

    @Override
    protected ViewHolder<Face.Bean> onCreateViewHolder(View root, int viewType) {
        return new FaceHolder(root);
    }
}
