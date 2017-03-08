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
			
		} else if (Application.platform == RuntimePlatform.IPhonePlayer) {
			OpenCameraRoll (Application.temporaryCachePath + "/tempImage");
		} else if (Application.platform == RuntimePlatform.Android) {
			
		}
	}

	public void SetImage (string path)
	{
		Texture2D texture2D = new Texture2D (2, 2);
		texture2D.LoadImage (System.IO.File.ReadAllBytes (path));
		Sprite sprite = Sprite.Create (texture2D, new Rect (0, 0, texture2D.width, texture2D.height), 0.5f * Vector2.one);
		image.sprite = sprite;
	}
}
