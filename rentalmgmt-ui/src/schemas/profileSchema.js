import { z } from 'zod';

// 1. Shared atomic definitions
export const emailSchema = z.string().min(1, 'Email is required').email('Invalid email format');
export const passwordSchema = z.string().min(8, 'Password must be at least 8 characters');
export const phoneSchema = z.string()
    .min(11, 'Phone number must be at least 11 characters')
    .max(12, 'Phone number must not exceed 12 characters')
    .regex(/^\d+$/, 'Phone number must contain only digits');

// 2. Base Profile Schema (Used for both)
export const profileBaseSchema = z.object({
    fullName: z.string().min(1, 'Full name is required').default(''),
    displayName: z.string().min(1, 'Display name is required').default(''),
    role: z.enum(['landlord', 'tenant']).default('landlord'),
    email: emailSchema,
    phone: phoneSchema,
    emergencyContact: phoneSchema,
    street: z.string().min(1, 'Street address is required').default(''),
    postalCode: z.string().min(1, 'Postal code is required').default(''),
    city: z.string().min(1, 'City is required').default(''),
    state: z.string().min(1, 'State is required').default(''),
    country: z.string().length(2, 'Country must be a 2-character ISO code').default(''),
});

// Variant A: For Registration (Required Passwords)
export const userRegisterSchema = profileBaseSchema.extend({
    password: passwordSchema,
    confirmPassword: z.string().min(1, 'Please confirm your password'),
    agree: z.boolean().refine((val) => val === true, { message: 'You must agree to the information verification' })
}).refine((data) => data.password === data.confirmPassword, {
    message: "Password and confirm password do not match",
    path: ["confirmPassword"]
});

// Variant B: For Profile Edit (Optional Passwords)
export const profileEditSchema = profileBaseSchema.extend({
    password: passwordSchema.optional().or(z.literal('')),
    confirmPassword: z.string().optional().or(z.literal(''))
}).refine((data) => {
    if (data.password && data.password !== data.confirmPassword) return false;
    return true;
}, {
    message: "Password and confirm password do not match",
    path: ["confirmPassword"]
});