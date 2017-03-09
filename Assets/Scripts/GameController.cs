using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using System.Runtime.InteropServices;

public class GameController : MonoBehaviour
{
	[DllImport ("__Internal")]
	private static extern float OpenCameraRoll (string path);

	public Image image;

	public void OnButtonClick ()
	{
		Debug.Log ("click");

		if (Application.isEditor) {
			
		} else
		{
		    switch (Application.platform)
		    {
		        case RuntimePlatform.IPhonePlayer:
		            OpenCameraRoll(Application.temporaryCachePath + "/tempImage");
		            break;
		        case RuntimePlatform.Android:
		            var nativeDialog = new AndroidJavaClass("com.wapa5pow.plugin.CameraRoll");
		            var unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		            var context = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");

		            context.Call("runOnUiThread", new AndroidJavaRunnable(() =>
		            {
		                nativeDialog.CallStatic(
		                    "openCameraRoll",
		                    context,
		                    Application.temporaryCachePath + "/tempImage"
		                );
		            }));
		            break;
		        default:
		            throw new ArgumentOutOfRangeException();
		    }
		}
	}

	public void SetImage (string path)
	{
		var texture2D = new Texture2D (2, 2);
		texture2D.LoadImage (System.IO.File.ReadAllBytes (path));
		var sprite = Sprite.Create (texture2D, new Rect (0, 0, texture2D.width, texture2D.height), 0.5f * Vector2.one);
		image.sprite = sprite;
	}
}
