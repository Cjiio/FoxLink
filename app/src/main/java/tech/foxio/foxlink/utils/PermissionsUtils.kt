package tech.foxio.foxlink.utils

import android.content.Context
import android.widget.Toast
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions

class PermissionsUtils {
    companion object {
        fun checkPermissions(context: Context, callback: Unit, vararg permissions: String) {
            XXPermissions.with(context)
                .permission(permissions)
                .request(
                    object : OnPermissionCallback {
                        override fun onGranted(
                            permissions: MutableList<String>,
                            allGranted: Boolean
                        ) {
                            if (!allGranted) {
                                Toast.makeText(
                                    context,
                                    "获取权限失败，将无法使用",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return
                            }
                            return callback
                        }

                        override fun onDenied(
                            permissions: MutableList<String>,
                            doNotAskAgain: Boolean
                        ) {
                            if (doNotAskAgain) {
                                XXPermissions.startPermissionActivity(context, permissions)
                            } else {
                                Toast.makeText(
                                    context,
                                    "获取权限失败，将无法使用",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                )
        }
    }
}