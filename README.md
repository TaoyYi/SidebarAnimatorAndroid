## SidebarAnimatorAndroid
一个使用简单(只需两行代码)并且滑动十分流畅的侧滑栏动画,支持所有类型的View定义为侧滑栏与开关.

## 效果图
![](https://github.com/TaoyYi/md-image/blob/master/SidebarAnimatorAndroid.gif)
![](https://github.com/TaoyYi/md-image/blob/master/SidebarAnimatorAndroid2.gif)

## 使用方法
#### 添加依赖
	compile 'com.yt:sidebar-animator:1.1.2'
#### 使用
* .xml
	
		<FrameLayout android:id="@+id/sidebar"
		                 android:layout_width="match_parent"
		                 android:layout_height="match_parent"
		                 android:layout_gravity="start"
		                 android:background="#00ff00"
		    />
		    <com.yt.sidebar_animator.SidebarLayout
		        android:id="@+id/sidebar_layout"
		        android:layout_width="match_parent"
		        android:layout_height="match_parent"
		        android:background="#000000"
		    >
		
		        <TextView android:layout_width="50dp" android:layout_height="50dp"
		                  android:background="#00ffff"
		                  android:text="点我打开侧滑栏"
		                  android:id="@+id/sidebar_hold"
		        />
		
		    </com.yt.sidebar_animator.SidebarLayout>
* .java
	* 初始化控件 
	
			//初始化fragment (sidebar-animator支持所有的view用来做侧边栏,并不局限与fragment,本dome就以fragment为例)
				        FragmentManager fm = getSupportFragmentManager();
				        mSidebarFragment = (SidebarFragment) fm.findFragmentById(R.id.sidebar);
				        if (mSidebarFragment == null) {
				            mSidebarFragment = new SidebarFragment();
				            fm.beginTransaction().add(R.id.sidebar, mSidebarFragment).commit();
				        }
				//SidebarLayout
				        mSidebarLayout = (SidebarLayout) findViewById(R.id.sidebar_layout);
				//开关(同样支持任意view,这里以textview举例)
				        mSidebarhold = (TextView) findViewById(R.id.sidebar_hold);

	* 重点 :

	        //top 1 : 重点来了 : 绑定侧滑栏
	        mSidebarLayout.bindSidebar(mSidebarFragment.getView(), mSidebarhold, width);  // 侧滑栏 , 侧滑栏开关 , 屏幕的宽度
	* 将touch事件传递给SidebarLayout
	
				@Override
			    public boolean dispatchTouchEvent(MotionEvent ev) {
			        //top 2 : 将touch事件传给SidebarLayout
			        mSidebarLayout.LayoutTouchEvent(ev);
			        return super.dispatchTouchEvent(ev);
			    }

## API : 
* 添加动画监听(1.1.2)
	
		public void addAnimatorListener(Animator.AnimatorListener animatorListener)

## else
* 获取屏幕宽度的API:
			       
        WindowManager wm = (MainActivity.this).getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
* 反馈邮箱 :
	* 602806406@qq.com
