package cn.sgr.zmr.com.sgr.Modules.My.Profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Common.Login.LoginActivity;
import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;
import cn.sgr.zmr.com.sgr.Modules.My.More.RetsetPwd.RetsetPwdActivity;
import cn.sgr.zmr.com.sgr.Modules.My.Profile.EditName.EditNameActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.ImageUtil;
import cn.sgr.zmr.com.sgr.Utils.util.MyConfig;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class ProfileFragment extends BaseFragment implements ProfileContract.View{

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.rel_reset_pwd)
    View   rel_reset_pwd;

    @BindView(R.id.rel_name)
    View   rel_name;

    @BindView(R.id.line_pwd)
    View   line_pwd;



    @BindView(R.id.rel_head)
    View   rel_head;

    @BindView(R.id.btn_login_out)
    Button btn_login_out;

    private File mFileZoomDir;
    private String mImagePath;

    private ProfileContract.Presenter mPresenter;
    private Dialog mDialog;
    private File mFileDir;
    private File mFile;
    private String mFileName = "";
    private final int REQUEST_CAMERA = 1;
    private final int REQUEST_PHOTO = 2;
    private final int REQUEST_PHOTOZOOM = 3;

    //单例 模式
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
    //   构造方法
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    //初始化控件
    private void initView() {
        top_view_title.setText(getResources().getString(R.string.my_profile));
        top_view_back.setVisibility(View.VISIBLE);
        if(UserInfo.getInstance(getActivity()).geThird_id()!=null&&UserInfo.getInstance(getActivity()).geThird_id().length()<3){//表示的是第三方登录
            rel_reset_pwd.setVisibility(View.GONE);
            line_pwd.setVisibility(View.GONE);
        }
    }

    //监听按钮
    @OnClick({R.id.top_view_back,R.id.rel_reset_pwd,R.id.rel_name,R.id.rel_head,R.id.btn_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
                getActivity().finish();
                break;
            case R.id.rel_reset_pwd:
                Utils.toNextActivity(getActivity(), RetsetPwdActivity.class);
                break;
            case R.id.rel_name://修改昵称
                Utils.toNextActivity(getActivity(),EditNameActivity.class);
                break;
            case R.id.rel_head://修改头像
                showDialog();
                break;
            case R.id.btn_login_out:
                UserInfo.getInstance(getActivity()).clearUserInfo();
                Utils.toNextActivity(getActivity(), LoginActivity.class);
                getActivity().finish();
                break;
        }
    }

    //弹出选择照片的入口
    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.photo_dialog, null);
        mDialog = new Dialog(getActivity(), R.style.dialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
        LinearLayout requsetCameraLayout = (LinearLayout) view
                .findViewById(R.id.register_camera);
        LinearLayout requestPhotoLayout = (LinearLayout) view
                .findViewById(R.id.register_photo);
        requsetCameraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDialog.dismiss();
                if (mFileDir == null) {
                    mFileDir = new File(MyConfig.FILEPATH_MY + "img/");
                    if (!mFileDir.exists()) {
                        mFileDir.mkdirs();
                    }
                }
                mFileName = MyConfig.FILEPATH_MY + "img/" + "temp.jpg";
                mFile = new File(mFileName);
                Uri imageuri = Uri.fromFile(mFile);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
                intent.putExtra("return-data", false);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
        requestPhotoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDialog.dismiss();
                Intent picture = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, REQUEST_PHOTO);

            }
        });
    }
    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                File files = new File(mFileName);
                if (files.exists()) {
                    mImagePath = mFileName;
                    mImagePath = startPhotoZoom(Uri.fromFile(new File(
                            mImagePath)));
                }
            } else if (requestCode == REQUEST_PHOTO) {
                Uri selectedImage = data.getData();
                mImagePath = startPhotoZoom(selectedImage);
            } else if (requestCode == REQUEST_PHOTOZOOM) {
                File f = new File(mImagePath);

                if (f.exists()) {
                    File file = new File(ImageUtil.zoomImage(mImagePath, 350));
                    /*mUserBalance.changeAvatar(file);// 上传本地图片
                    updatePhoto(mImagePath);// 第三方上传头像*/
                } else {
                    Toast.makeText(getActivity(),"图片不存在",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private String startPhotoZoom(Uri uri) {

        if (mFileZoomDir == null) {
            mFileZoomDir = new File(MyConfig.FILEPATH + "img/");
            if (!mFileZoomDir.exists()) {
                mFileZoomDir.mkdirs();
            }
        }

        String fileName;
        fileName = "/temp.jpg";

        String filePath = mFileZoomDir + fileName;
        File loadingFile = new File(filePath);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 400);
        intent.putExtra("aspectY", 400);
        intent.putExtra("output", Uri.fromFile(loadingFile));// 输出到文件
        intent.putExtra("outputFormat", "PNG");// 返回格式
        intent.putExtra("noFaceDetection", true); // 去除面部检测
        intent.putExtra("return-data", false); // 不要通过Intent传递截获的图片
        startActivityForResult(intent, REQUEST_PHOTOZOOM);

        return filePath;

    }
}
