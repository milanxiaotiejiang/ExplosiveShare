using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AccelerationCtrl : MonoBehaviour
{
    private float speed = 0.3f;
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        transform.Translate(Input.acceleration.x * speed, 0, -Input.acceleration.z * speed);
    }
}
