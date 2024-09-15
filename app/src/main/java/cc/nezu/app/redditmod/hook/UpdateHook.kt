package cc.nezu.app.redditmod.hook

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import com.github.kyuubiran.ezxhelper.ClassHelper.Companion.classHelper
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.ConstructorFinder
import com.github.kyuubiran.ezxhelper.finders.FieldFinder
import com.github.kyuubiran.ezxhelper.finders.MethodFinder
import java.lang.reflect.Modifier

object UpdateHook : BaseHook() {
    override val name: String = "UpdateHook"

    override fun init() {
        MethodFinder.fromClass("com.reddit.startup.appupdate.AppUpdateInitializer")
            .filterByParamTypes(Context::class.java)
            .filterByReturnType(Object::class.java)
            .first()
            .createHook {
                replace {
                    return@replace null
                }
            }
    }
}