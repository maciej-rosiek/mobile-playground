using System;
using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using Object = UnityEngine.Object;
using Random = System.Random;

public class BarrelGenerator : MonoBehaviour
{

    public int GenerationSpeed = 4000;
    public Barrel barrel1;
    public Barrel barrel2;
    public Barrel barrel3;
    private List<Barrel> barrels;
    private List<Barrel> barrelsToReuse = new List<Barrel>();

    private decimal lastGenerationTime;

	void Start ()
	{
	    lastGenerationTime = GetTime();
	    barrels = new List<Barrel> {barrel1, barrel2, barrel3};

	    foreach (Barrel barrel in barrels)
	    {
	        for (int i = 0; i < 3; i++)
	        {
	            Barrel copy = DuplicateBarrel(barrel);
	            barrelsToReuse.Add(copy);
	        }
	    }
	}

    void Update ()
    {
        if (GetTime() - lastGenerationTime > GenerationSpeed)
        {
            GenerateBarrel();
            lastGenerationTime = GetTime();
        }
    }

    private void GenerateBarrel()
    {
        ShuffleArray(barrelsToReuse);
        foreach (Barrel barrel in barrelsToReuse)
        {
            if (!barrel.Move)
            {
                barrel.transform.position = barrel.OriginalPosition;
                barrel.Move = true;
                return;
            }
        }
        int barrelNum = (new Random()).Next(2);
        Barrel original = barrels[barrelNum];
        Barrel copy = DuplicateBarrel(original);
        copy.Move = true;
        barrelsToReuse.Add(copy);
    }

    private Barrel DuplicateBarrel(Barrel original)
    {
        Barrel instantiate = (Barrel) Instantiate(original, original.transform.position, original.transform.rotation);
        instantiate.OriginalPosition = original.transform.position;
        return instantiate;
    }

    private static decimal GetTime()
    {
        return DateTime.Now.Ticks / (decimal)TimeSpan.TicksPerMillisecond;
    }

    private static void ShuffleArray<T>(List<T> list)
    {
        System.Random random = new System.Random();
        for (int i = 0; i < list.Count; i++)
        {
            int j = random.Next(i, list.Count);
            T temp = list[i]; list[i] = list[j]; list[j] = temp;
        }
    }

}
