/*
 * Copyright 2014-2017 pushbit <pushbit@gmail.com>
 *
 * This file is part of Sprockets.
 *
 * Sprockets is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Sprockets is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with Sprockets. If
 * not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.sprockets.content;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.admin.DevicePolicyManager;
import android.app.backup.BackupManager;
import android.app.job.JobScheduler;
import android.app.usage.NetworkStatsManager;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.RestrictionsManager;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutManager;
import android.hardware.ConsumerIrManager;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.midi.MidiManager;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.media.tv.TvInputManager;
import android.net.ConnectivityManager;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.DropBoxManager;
import android.os.HardwarePropertiesManager;
import android.os.PowerManager;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.health.SystemHealthManager;
import android.os.storage.StorageManager;
import android.print.PrintManager;
import android.telecom.TelecomManager;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;

import static android.content.Context.ACCESSIBILITY_SERVICE;
import static android.content.Context.ACCOUNT_SERVICE;
import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.APPWIDGET_SERVICE;
import static android.content.Context.APP_OPS_SERVICE;
import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.BATTERY_SERVICE;
import static android.content.Context.BLUETOOTH_SERVICE;
import static android.content.Context.CAMERA_SERVICE;
import static android.content.Context.CAPTIONING_SERVICE;
import static android.content.Context.CARRIER_CONFIG_SERVICE;
import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.CONSUMER_IR_SERVICE;
import static android.content.Context.DEVICE_POLICY_SERVICE;
import static android.content.Context.DISPLAY_SERVICE;
import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.DROPBOX_SERVICE;
import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.HARDWARE_PROPERTIES_SERVICE;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.INPUT_SERVICE;
import static android.content.Context.JOB_SCHEDULER_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.LAUNCHER_APPS_SERVICE;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MEDIA_PROJECTION_SERVICE;
import static android.content.Context.MEDIA_ROUTER_SERVICE;
import static android.content.Context.MEDIA_SESSION_SERVICE;
import static android.content.Context.MIDI_SERVICE;
import static android.content.Context.NETWORK_STATS_SERVICE;
import static android.content.Context.NFC_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.NSD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static android.content.Context.PRINT_SERVICE;
import static android.content.Context.RESTRICTIONS_SERVICE;
import static android.content.Context.SEARCH_SERVICE;
import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.SHORTCUT_SERVICE;
import static android.content.Context.STORAGE_SERVICE;
import static android.content.Context.SYSTEM_HEALTH_SERVICE;
import static android.content.Context.TELECOM_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;
import static android.content.Context.TELEPHONY_SUBSCRIPTION_SERVICE;
import static android.content.Context.TEXT_SERVICES_MANAGER_SERVICE;
import static android.content.Context.TV_INPUT_SERVICE;
import static android.content.Context.UI_MODE_SERVICE;
import static android.content.Context.USAGE_STATS_SERVICE;
import static android.content.Context.USB_SERVICE;
import static android.content.Context.USER_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;
import static android.content.Context.WIFI_P2P_SERVICE;
import static android.content.Context.WIFI_SERVICE;
import static android.content.Context.WINDOW_SERVICE;

/**
 * Utility methods for working with {@link Context#getSystemService(String) system services} and
 * other Managers.
 */
public class Managers {
    private Managers() {
    }

    public static AccessibilityManager accessibility(Context context) {
        return (AccessibilityManager) context.getSystemService(ACCESSIBILITY_SERVICE);
    }

    public static AccountManager account(Context context) {
        return (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
    }

    public static ActivityManager activity(Context context) {
        return (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
    }

    public static AlarmManager alarm(Context context) {
        return (AlarmManager) context.getSystemService(ALARM_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static AppWidgetManager appWidget(Context context) {
        return (AppWidgetManager) context.getSystemService(APPWIDGET_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static AppOpsManager appOps(Context context) {
        return (AppOpsManager) context.getSystemService(APP_OPS_SERVICE);
    }

    public static AudioManager audio(Context context) {
        return (AudioManager) context.getSystemService(AUDIO_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static BatteryManager battery(Context context) {
        return (BatteryManager) context.getSystemService(BATTERY_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static BluetoothManager bluetooth(Context context) {
        return (BluetoothManager) context.getSystemService(BLUETOOTH_SERVICE);
    }

    private static BackupManager sBackup;

    /**
     * @since 2.4.0
     */
    public static BackupManager backup(Context context) {
        if (sBackup == null) {
            sBackup = new BackupManager(context.getApplicationContext());
        }
        return sBackup;
    }

    /**
     * @since 4.0.0
     */
    public static CameraManager camera(Context context) {
        return (CameraManager) context.getSystemService(CAMERA_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static CaptioningManager captioning(Context context) {
        return (CaptioningManager) context.getSystemService(CAPTIONING_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static CarrierConfigManager carrierConfig(Context context) {
        return (CarrierConfigManager) context.getSystemService(CARRIER_CONFIG_SERVICE);
    }

    public static ClipboardManager clipboard(Context context) {
        return (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
    }

    public static ConnectivityManager connectivity(Context context) {
        return (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static ConsumerIrManager consumerIr(Context context) {
        return (ConsumerIrManager) context.getSystemService(CONSUMER_IR_SERVICE);
    }

    public static DevicePolicyManager devicePolicy(Context context) {
        return (DevicePolicyManager) context.getSystemService(DEVICE_POLICY_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static DisplayManager display(Context context) {
        return (DisplayManager) context.getSystemService(DISPLAY_SERVICE);
    }

    public static DownloadManager download(Context context) {
        return (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
    }

    public static DropBoxManager dropBox(Context context) {
        return (DropBoxManager) context.getSystemService(DROPBOX_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static FingerprintManager fingerprint(Context context) {
        return (FingerprintManager) context.getSystemService(FINGERPRINT_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static HardwarePropertiesManager hardwareProperties(Context context) {
        return (HardwarePropertiesManager) context.getSystemService(HARDWARE_PROPERTIES_SERVICE);
    }

    public static InputMethodManager inputMethod(Context context) {
        return (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
    }

    public static InputManager input(Context context) {
        return (InputManager) context.getSystemService(INPUT_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static JobScheduler jobScheduler(Context context) {
        return (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
    }

    public static KeyguardManager keyguard(Context context) {
        return (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static LauncherApps launcherApps(Context context) {
        return (LauncherApps) context.getSystemService(LAUNCHER_APPS_SERVICE);
    }

    public static LayoutInflater layoutInflater(Context context) {
        return (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    public static LocationManager location(Context context) {
        return (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static MediaProjectionManager mediaProjection(Context context) {
        return (MediaProjectionManager) context.getSystemService(MEDIA_PROJECTION_SERVICE);
    }

    public static MediaRouter mediaRouter(Context context) {
        return (MediaRouter) context.getSystemService(MEDIA_ROUTER_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static MediaSessionManager mediaSession(Context context) {
        return (MediaSessionManager) context.getSystemService(MEDIA_SESSION_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static MidiManager midi(Context context) {
        return (MidiManager) context.getSystemService(MIDI_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static NetworkStatsManager networkStats(Context context) {
        return (NetworkStatsManager) context.getSystemService(NETWORK_STATS_SERVICE);
    }

    public static NfcManager nfc(Context context) {
        return (NfcManager) context.getSystemService(NFC_SERVICE);
    }

    public static NotificationManager notification(Context context) {
        return (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }

    public static NsdManager nsd(Context context) {
        return (NsdManager) context.getSystemService(NSD_SERVICE);
    }

    public static PowerManager power(Context context) {
        return (PowerManager) context.getSystemService(POWER_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static PrintManager print(Context context) {
        return (PrintManager) context.getSystemService(PRINT_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static RestrictionsManager restrictions(Context context) {
        return (RestrictionsManager) context.getSystemService(RESTRICTIONS_SERVICE);
    }

    public static SearchManager search(Context context) {
        return (SearchManager) context.getSystemService(SEARCH_SERVICE);
    }

    public static SensorManager sensor(Context context) {
        return (SensorManager) context.getSystemService(SENSOR_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static ShortcutManager shortcut(Context context) {
        return (ShortcutManager) context.getSystemService(SHORTCUT_SERVICE);
    }

    public static StorageManager storage(Context context) {
        return (StorageManager) context.getSystemService(STORAGE_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static SubscriptionManager telephonySubscription(Context context) {
        return (SubscriptionManager) context.getSystemService(TELEPHONY_SUBSCRIPTION_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static SystemHealthManager systemHealth(Context context) {
        return (SystemHealthManager) context.getSystemService(SYSTEM_HEALTH_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static TelecomManager telecom(Context context) {
        return (TelecomManager) context.getSystemService(TELECOM_SERVICE);
    }

    public static TelephonyManager telephony(Context context) {
        return (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
    }

    public static TextServicesManager textServices(Context context) {
        return (TextServicesManager) context.getSystemService(TEXT_SERVICES_MANAGER_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static TvInputManager tvInput(Context context) {
        return (TvInputManager) context.getSystemService(TV_INPUT_SERVICE);
    }

    public static UiModeManager uiMode(Context context) {
        return (UiModeManager) context.getSystemService(UI_MODE_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static UsageStatsManager usageStats(Context context) {
        return (UsageStatsManager) context.getSystemService(USAGE_STATS_SERVICE);
    }

    public static UsbManager usb(Context context) {
        return (UsbManager) context.getSystemService(USB_SERVICE);
    }

    /**
     * @since 4.0.0
     */
    public static UserManager user(Context context) {
        return (UserManager) context.getSystemService(USER_SERVICE);
    }

    public static Vibrator vibrator(Context context) {
        return (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
    }

    public static WifiP2pManager wifiP2p(Context context) {
        return (WifiP2pManager) context.getSystemService(WIFI_P2P_SERVICE);
    }

    public static WifiManager wifi(Context context) {
        return (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
    }

    public static WindowManager window(Context context) {
        return (WindowManager) context.getSystemService(WINDOW_SERVICE);
    }
}
