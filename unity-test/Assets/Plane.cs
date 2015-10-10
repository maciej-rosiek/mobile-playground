using System;
using UnityEngine;
using System.Collections;

public class Plane : MonoBehaviour {

    static float _deadzone = 0.1f;
    Animator animator;
    int speed = 6;
    public GameObject plane;
    int moveDirection = 0;

	void Start () {
        animator = GetComponent<Animator>();
	}

    private int lastMousePosition = 0;

    void Update()
    {
        float axisInput = Input.GetAxisRaw("Horizontal");
        moveDirection = (int) axisInput;
        if (axisInput == 0)
        {
            if (Input.GetButton("Fire1"))
            {
               moveDirection = Math.Sign(Input.mousePosition.x - lastMousePosition);     
            }
            if (Input.GetButtonUp("Fire1"))
            {
                lastMousePosition = 0;
            }
        }
        animator.SetInteger("speed", - Math.Sign(moveDirection));
        if (moveDirection > 0 && transform.position.x < 3.5)
        {
            transform.Translate(Vector3.right * speed * Time.deltaTime);
        }
        if (moveDirection < 0 && transform.position.x > -3.5)
        {
            transform.Translate(Vector3.left * speed * Time.deltaTime);
        }

        lastMousePosition = (int)Input.mousePosition.x;
    }
}
