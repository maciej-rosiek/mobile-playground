using System;
using UnityEngine;
using System.Collections;

public class Barrel : MonoBehaviour
{
    public bool Move = false;
    public Vector3 OriginalPosition;
    private int speed = 2;

	void Update ()
	{
	    if (Move)
	    {
	        transform.Translate(Vector3.down * Time.deltaTime * speed);
	        if (transform.position.y < -7)
	        {
	            Move = false;
	        }
	    }
	}
}
