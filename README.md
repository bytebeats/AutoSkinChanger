# AutoSkinChanger: change an app's theme and style automatically
The core codes of how to change app's skin automatically. 
Android 自动换肤核心代码.
诸如网易云音乐, 招商银行等应用, 在选择换肤之后, 应用在没有重启的情况下, 应用的界面发生了即时的变化.

## 自动换肤的原理
在通过 `Activity#setContentView(...)`设置 Activity 的界面的时候, 会调用到 `LayoutInflater#inflate(resources, root, attachToRoot)` -> `inflate(parser, root, attachToRoot)` -> `createViewFromTag()` -> `tryCreateView(parent, name, context, attrs)`
在这里可以先查看一下`tryCreateView(parent, name, context, attrs)`源码:

        View view;
        if (mFactory2 != null) {
            view = mFactory2.onCreateView(parent, name, context, attrs);
        } else if (mFactory != null) {
            view = mFactory.onCreateView(name, context, attrs);
        } else {
            view = null;
        }

        if (view == null && mPrivateFactory != null) {
            view = mPrivateFactory.onCreateView(parent, name, context, attrs);
        }

        return view;

可以看到 在 `mFactory2 != null` 的时候, 使用 `mFactory2.onCreateView(parent, name, context, attrs)` 来创建 `View`

因而可以在 `Activity#setContentView` 调用之前通过运行时反射将 `mFactory2` 替换为我们自己的 `LayoutInflater.Factory2`

具体的细节还需要查看本项目源码.

## Stargazers over time

[![Stargazers over time](https://starchart.cc/bytebeats/AutoSkinChanger.svg)](https://starchart.cc/bytebeats/AutoSkinChanger)


## MIT License

    Copyright (c) 2021 Chen Pan

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
