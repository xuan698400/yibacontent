package com.xuan.app.yiba;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xuan.app.yiba.utils.AlertDialogUtils;
import com.xuan.app.yiba.utils.ContextUtils;
import com.xuan.app.yiba.utils.HttpUtils;
import com.xuan.app.yiba.utils.ProgressDialogUtils;
import com.xuan.app.yiba.utils.SystemConfigModel;
import com.xuan.app.yiba.utils.ToastUtils;
import com.xuan.app.yiba.utils.TranslateUtils;
import com.xuan.app.yiba.utils.Validators;

public class MainActivity extends Activity {

    private Button button; // 走一个按钮
    private EditText fromWordEditText;// 输入
    private TextView toWordTextView;// 输出
    private TextView explain;
    private TextView explainWord;

    private final Handler handler = new Handler();
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 不显示标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        button = (Button) findViewById(R.id.button);
        fromWordEditText = (EditText) findViewById(R.id.fromWord);
        toWordTextView = (TextView) findViewById(R.id.toWord);
        explain = (TextView) findViewById(R.id.explain);
        explainWord = (TextView) findViewById(R.id.explainWord);

        progressDialog = new ProgressDialog(this);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断网络
                if (!ContextUtils.hasNetwork(MainActivity.this)) {
                    ToastUtils.displayTextShort(MainActivity.this, "亲，网络不可用哦");
                    return;
                }

                // 隐藏键盘输入
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }

                // 处理翻译
                final String fromWord = fromWordEditText.getEditableText().toString();

                if (Validators.isEmpty(fromWord)) {
                    ToastUtils.displayTextShort(MainActivity.this, "输入内容不能为空");
                    return;
                }

                ProgressDialogUtils.show("我在奋力翻译中", progressDialog);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final List<String> explainsList = new ArrayList<String>();
                            final String toWord = TranslateUtils.getTranslateWord(fromWord, explainsList);// 查询有道翻译API
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    explain.setText("");
                                    explainWord.setText("");

                                    toWordTextView.setText(toWord);

                                    // 显示解释
                                    if (!Validators.isEmpty(explainsList)) {
                                        explain.setText("一些别的解释：");
                                        StringBuilder sb = new StringBuilder();
                                        for (String explainWord : explainsList) {
                                            sb.append(explainWord).append("，");
                                        }

                                        explainWord.setText(sb.toString().substring(0, sb.length() - 1));
                                    }
                                }
                            });
                        }
                        finally {
                            ProgressDialogUtils.dismiss(handler, progressDialog);
                        }
                    }
                }).start();
            }
        });
    }

    // 菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, 0, "关于");
        menu.add(Menu.NONE, 2, 0, "新版本检测");
        menu.add(Menu.NONE, 3, 0, "退出");
        return super.onCreateOptionsMenu(menu);
    }

    // 菜单选中
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
        case 1:
            AlertDialogUtils.displayAlert(MainActivity.this, "徐安2号出品",
                    "这是一款绝对绿色，绝对简单方便的翻译软件。我的宗旨就是，没有广告，没有插件，简单实用。如果有什么要的建议，欢迎交流。电：15858178400。非诚勿扰。", "晓得了");
            break;
        case 2:
            ProgressDialogUtils.show("我在努力检测中啊", progressDialog);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = HttpUtils.requestURL("http://blog.xuanner.com/yiba/updateInfo.php");
                        result = result.substring(result.indexOf('{'));

                        JSONObject object = new JSONObject(result);
                        String message = object.getString("message");
                        ProgressDialogUtils.dismiss(handler, progressDialog);

                        AlertDialogUtils.displayAlert(MainActivity.this, "版本更新提醒", "您当前使用的版本是"
                                + SystemConfigModel.instance(MainActivity.this).getVersionName() + "。" + message,
                                "晓得了", handler);
                    }
                    catch (Exception e) {
                        ProgressDialogUtils.dismiss(handler, progressDialog);

                        AlertDialogUtils.displayAlert(MainActivity.this, "版本更新提醒", "您当前使用的版本是"
                                + SystemConfigModel.instance(MainActivity.this).getVersionName() + "。", "晓得了", handler);
                    }
                }
            }).start();

            break;
        case 3:
            MainActivity.this.finish();
            break;
        }

        return super.onOptionsItemSelected(item);
    }

}
