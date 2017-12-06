SwipeToRefreshLayout 
================

Simple SwipetoRefresh viewgroup that allows you to add custom images and animations and swipe in both directions

To include in your project, add this to your build.gradle file:
```
   //SwipeToRefreshLayout
	        compile 'com.github.aliumujib:SwipeToRefresh:v1.01'
   ```

  

# Screenshots

|       ROW 1  |        ROW 2    |   
| ------------- |:-------------:| 
| <img src="gifs/fade.gif" width="350"/>    | <img src="gifs/rotate.gif" width="350"/>  |
| <img src="gifs/rotate_x.gif" width="350"/>  | <img src="gifs/fade.gif" width="350"/>  | 
| <img src="gifs/scale.gif" width="350"/>  |  |
========

### Usage

If you want an example on how to use it, you can find an example app in this repo.

```
<com.aliumujib.swipetorefresh.SwipeToRefreshLayout android:id="@+id/swipetorefreshlayout"
    android:layout_height="wrap_content"
    android:layout_width="wrap_content"
    app:srl_animation="fade"
    app:srl_direction="both"
    app:srl_icon="@mipmap/ic_launcher_round"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <android.support.v7.widget.RecyclerView xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:id="@+id/swipetorefreshlist"
        android:name="com.aliumujib.swipetorefreshdemo.DemoFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginLeft="16dp"
        android:layout_marginRight="16dp"
        app:layoutManager="LinearLayoutManager"
        tools:context="com.aliumujib.swipetorefreshdemo.DemoFragment"
        tools:listitem="@layout/fragment_demo_item" />

</com.aliumujib.swipetorefresh.SwipeToRefreshLayout>
```

```
swipetorefreshlayout.setOnRefreshListener {
            direction->
            Log.d(TAG, "Refresh triggered at " + if (direction == RefreshDirection.TOP) "top" else "bottom")
            Handler().postDelayed(Runnable {
                //Hide the refresh after 2sec
                activity.runOnUiThread(Runnable { swipetorefreshlayout.setRefreshing(false) })
            }, DISMISS_TIMEOUT)
        }
```

========

### Customization

#### Direction

* XML:
```
app:srl_direction="top"
```
OR
```
app:srl_direction="bottom"
```
OR
```
app:srl_direction="both"
```

* Programmatically:
```
swipetorefreshlayout.setDirection(RefreshDirection.TOP);
```
OR
```
swipetorefreshlayout.setDirection(RefreshDirection.BOTTOM);
```
OR
```
swipetorefreshlayout.setDirection(RefreshDirection.BOTH);
```

#### Animation

* XML:
```
app:srl_animation="fade"
```
OR
```
app:srl_animation="rotate"
```
OR
```
app:srl_animation="rotate_x"
```
OR
```
app:srl_animation="rotate_y"
```
OR
```
app:srl_animation="scale"
```

- 

#### Icon
* XML:
```
app:srl_icon="@your_icon_reference_here"
```

========


### License

```
The MIT License (MIT)

Copyright (c) 2017 Abdul-Mujeeb Aliu

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
