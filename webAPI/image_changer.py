import math
import os.path
import random
import time

import torch
from PIL import Image, ImageOps
from diffusers import StableDiffusionInstructPix2PixPipeline, EulerAncestralDiscreteScheduler


model_id = "timbrooks/instruct-pix2pix"
pipe = StableDiffusionInstructPix2PixPipeline.from_pretrained(model_id, torch_dtype=torch.float16,
                                                              safety_checker=None).to("cuda")
pipe.scheduler = EulerAncestralDiscreteScheduler.from_config(pipe.scheduler.config)
model_id = "timbrooks/instruct-pix2pix"


def transform_image_def_cfg(n, image_path, prompt, txt_cfg, img_cfg, name_type):
    input_image = Image.open(image_path)
    img_name = name_type
    if not os.path.isdir(img_name):
        os.mkdir(img_name)
    for it in range(n):
        seed, txt_cfg, img_cfg, edited_img = generate(input_image, instruction=prompt, steps=10,
                                                      seed=42, text_cfg_scale=txt_cfg,
                                                      image_cfg_scale=img_cfg)
        edited_img.save(f'{img_name}/_{txt_cfg}_{img_cfg}_{it + 1}' + '.png')

def generate(
        input_image: Image.Image,
        instruction: str,
        steps: int,
        seed: int,
        text_cfg_scale: float,
        image_cfg_scale: float,
):
    times = int(time.time_ns() % (1e9 + 7))
    random.seed(times)

    init_width, init_height = input_image.size
    factor = 512 / max(init_width, init_height)
    factor = math.ceil(min(init_width, init_height) * factor / 64) * 64 / min(init_width, init_height)
    width = int((init_width * factor) // 64) * 64
    height = int((init_height * factor) // 64) * 64
    input_image = ImageOps.fit(input_image, (width, height), method=Image.Resampling.LANCZOS)

    if instruction == "":
        return [input_image, seed]

    generator = torch.manual_seed(seed)
    edited_image = pipe(
        instruction, image=input_image,
        guidance_scale=text_cfg_scale, image_guidance_scale=image_cfg_scale,
        num_inference_steps=steps, generator=generator,
    ).images[0]
    return [seed, text_cfg_scale, image_cfg_scale, edited_image]


if __name__ == "__main__":
    transform_image_def_cfg(1, 'igore.jpg', 'make him wearing blazer', 8, 1.7, 'igore-new')
