package com.example.testim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mClick;
    private Button mClick1;
    private EditText mInputSend;
    private TextView mResult;
    private EditText mInputName;
    private EditText mWhoSend;
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息

            for (EMMessage message : messages) {

                EMMessageBody body = message.getBody();


            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }
        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        EMClient.getInstance().chatManager().addMessageListener(msgListener);

    }

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
        mClick1 = (Button) findViewById(R.id.click1);
        mClick1.setOnClickListener(this);
        mInputSend = (EditText) findViewById(R.id.send_input);
        mResult = (TextView) findViewById(R.id.result);
        mInputName = (EditText) findViewById(R.id.name_input);
        mWhoSend = (EditText) findViewById(R.id.send_who);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click:
                // TODO 20/04/27
                login();
                break;
            case R.id.click1:// TODO 20/04/27
                String content = mInputSend.getText().toString();

                String who = mWhoSend.getText().toString();
//创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(content, who);
//如果是群聊，设置chattype，默认是单聊
//                if (chatType == CHATTYPE_GROUP)
                message.setChatType(EMMessage.ChatType.GroupChat);
//发送消息
                EMClient.getInstance().chatManager().sendMessage(message);

                break;
            default:
                break;
        }
    }

    private void login() {

        String content = mInputName.getText().toString();
        String[] namePsw = content.split(",");
        EMClient.getInstance().login(namePsw[0], namePsw[1], new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "登录聊天服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}
