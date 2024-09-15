package cc.nezu.app.redditmod

import cc.nezu.app.redditmod.hook.BaseHook
import cc.nezu.app.redditmod.hook.UpdateHook
import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.LogExtensions.logexIfThrow
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

private const val PACKAGE_NAME_HOOKED = "com.reddit.frontpage"
private const val TAG = "redditmod"

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == PACKAGE_NAME_HOOKED) {
            // Init EzXHelper
            EzXHelper.initHandleLoadPackage(lpparam)
            EzXHelper.setLogTag(TAG)
            EzXHelper.setToastTag(TAG)
            // Init hooks
            initHooks(UpdateHook)
        }
    }

    private fun initHooks(vararg hook: BaseHook) {
        hook.forEach {
            runCatching {
                if (it.isInit) return@forEach
                it.init()
                it.isInit = true
                Log.i("Inited hook: ${it.name}")
            }.logexIfThrow("Failed init hook: ${it.name}")
        }
    }
}